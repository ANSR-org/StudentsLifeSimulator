package bg.ansr.simulator.studentsimulatorcore.controllers;

import bg.ansr.simulator.studentsimulatorcore.repositories.lecture.LectureRepository;
import bg.ansr.simulator.studentsimulatorcore.services.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class LectureController extends BaseController {

    private final LectureRepository lectureRepository;
    private final StudentService studentService;

    @Autowired
    public LectureController(LectureRepository lectureRepository,
                             StudentService studentService) {
        this.lectureRepository = lectureRepository;
        this.studentService = studentService;
    }

}
