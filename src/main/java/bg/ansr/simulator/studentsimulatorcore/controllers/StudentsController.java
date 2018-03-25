package bg.ansr.simulator.studentsimulatorcore.controllers;

import bg.ansr.simulator.studentsimulatorcore.repositories.student.StudentRepository;
import bg.ansr.simulator.studentsimulatorcore.services.student.StudentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@PreAuthorize("isAuthenticated()")
public class StudentsController extends BaseController {

    private final StudentService studentService;
    private final StudentRepository studentRepository;

    public StudentsController(StudentService studentService, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

    @GetMapping("/rankings")
    public ModelAndView rankings() {
        return this.view(this.studentRepository.findAllByOrderByPointsDesc());
    }



}
