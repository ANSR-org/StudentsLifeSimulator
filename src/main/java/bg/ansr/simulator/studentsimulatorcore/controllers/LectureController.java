package bg.ansr.simulator.studentsimulatorcore.controllers;

import bg.ansr.simulator.studentsimulatorcore.entities.BlockingEvent;
import bg.ansr.simulator.studentsimulatorcore.entities.Lecture;
import bg.ansr.simulator.studentsimulatorcore.entities.Student;
import bg.ansr.simulator.studentsimulatorcore.repositories.blockingEvent.BlockingEventRepository;
import bg.ansr.simulator.studentsimulatorcore.repositories.lecture.LectureRepository;
import bg.ansr.simulator.studentsimulatorcore.services.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

@Controller
public class LectureController extends BaseController {

    private final LectureRepository lectureRepository;
    private final BlockingEventRepository blockingEventRepository;
    private final StudentService studentService;

    @Autowired
    public LectureController(LectureRepository lectureRepository,
                             BlockingEventRepository blockingEventRepository,
                             StudentService studentService) {
        this.lectureRepository = lectureRepository;
        this.blockingEventRepository = blockingEventRepository;
        this.studentService = studentService;
    }

    @PostMapping("/lecture/attend/{id}")
    public String attend(@PathVariable Long id) throws Exception {
        Lecture lecture = this.lectureRepository.findOne(id);
        Student student = this.studentService.current();

        if (Objects.isNull(lecture)) {
            throw new Exception("Lecture does not exist");
        }

        if (!lecture.getSpecialty().equals(student.getSpecialty())) {
            throw new Exception("Lecture is not part of current user specialty");
        }

        LocalDateTime timePoint = LocalDateTime.now();
        Date dateNow = new Date();
        if (lecture.getEndedAt().compareTo(dateNow) > 0) {
            throw new Exception("This lecture has ended");
        }

        if (lecture.getStudents().contains(student)) {
            throw new Exception("Can not attend the same lecture twice");
        }

        LocalTime localTimeNow = new Time(dateNow.getTime()).toLocalTime();
        LocalTime localTimeLecture = lecture.getStartedAt().toLocalTime();

        long minDiff = Math.abs(localTimeLecture.until(localTimeNow, ChronoUnit.SECONDS));
        if (minDiff > 60) {
            throw new Exception("This lecture is not available");
        }

        BlockingEvent event = new BlockingEvent();
        if (lecture.getStartedAt().compareTo(dateNow) < 0) {
            event.setStartedAt(lecture.getStartedAt());
        } else {
            event.setStartedAt(new Time(dateNow.getTime()));
        }

        event.setEndedAt(lecture.getEndedAt());

        student.getBlockingEvents().add(event);
        decreaseEnergy(lecture, student);
        event.setStudent(student);
        event.setLastUrl("/specialty/" + lecture.getSpecialty().getId() + "/lectures/mandatory/");
        lecture.getStudents().add(student);
        student.setCurrentLecture(lecture);

        lectureRepository.save(lecture);
        blockingEventRepository.save(event);
        studentService.save(student);
        return "students/index";
    }

    private void decreaseEnergy(Lecture lecture, Student student) throws Exception {
        Long energy = student.getEnergy() - lecture.getEnergyCost();
        if (energy < 0) {
            throw new Exception("Not enough energy");
        }

        student.setEnergy(energy);
    }
}
