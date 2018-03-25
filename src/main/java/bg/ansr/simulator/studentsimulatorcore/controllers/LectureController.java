package bg.ansr.simulator.studentsimulatorcore.controllers;

import bg.ansr.simulator.studentsimulatorcore.entities.BlockingEvent;
import bg.ansr.simulator.studentsimulatorcore.entities.Lecture;
import bg.ansr.simulator.studentsimulatorcore.entities.Student;
import bg.ansr.simulator.studentsimulatorcore.repositories.blockingEvent.BlockingEventRepository;
import bg.ansr.simulator.studentsimulatorcore.repositories.lecture.LectureRepository;
import bg.ansr.simulator.studentsimulatorcore.services.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Time;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
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

    @GetMapping("/lecture/attend/{id}")
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
        LocalTime localTimeEndLecture = lecture.getEndedAt().toLocalTime();

        long minDiff = Math.abs(localTimeLecture.until(localTimeNow, ChronoUnit.SECONDS));
        double seconds = Math.floor(Duration.between(localTimeLecture, localTimeEndLecture).dividedBy(3).getSeconds());

        if (minDiff > seconds) {
            throw new Exception("This lecture is not available");
        }

        BlockingEvent event = getBlockingEvent(student, localTimeLecture, localTimeEndLecture);
        event.setLastUrl("/specialty/" + lecture.getSpecialty().getId() + "/lectures/mandatory/");
        student.getBlockingEvents().add(event);

        decreaseEnergy(lecture, student);
        lecture.getStudents().add(student);
        student.setCurrentLecture(lecture);

        lectureRepository.save(lecture);
        blockingEventRepository.save(event);
        studentService.save(student);
        return "students/index";
    }

    private BlockingEvent getBlockingEvent(Student student, LocalTime localTimeLecture, LocalTime localTimeEndLecture) {
        BlockingEvent event = new BlockingEvent();
        event.setStartedAt(LocalDateTime.now()
                .withHour(localTimeLecture.getHour())
                .withMinute(localTimeLecture.getMinute())
                .withSecond(localTimeLecture.getSecond()));

        event.setEndedAt(LocalDateTime.now()
                .withHour(localTimeEndLecture.getHour())
                .withMinute(localTimeEndLecture.getMinute())
                .withSecond(localTimeEndLecture.getSecond()));

        event.setStudent(student);
        return event;
    }

    private void decreaseEnergy(Lecture lecture, Student student) throws Exception {
        Long energy = student.getEnergy() - lecture.getEnergyCost();
        if (energy < 0) {
            throw new Exception("Not enough energy");
        }

        student.setEnergy(energy);
    }
}
