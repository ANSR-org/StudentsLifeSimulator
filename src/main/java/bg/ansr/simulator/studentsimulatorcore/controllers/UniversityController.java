package bg.ansr.simulator.studentsimulatorcore.controllers;

import bg.ansr.simulator.studentsimulatorcore.repositories.university.UniversityRepository;
import bg.ansr.simulator.studentsimulatorcore.services.student.StudentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@PreAuthorize("isAuthenticated()")
public class UniversityController extends BaseController {

    private final StudentService studentService;
    private final UniversityRepository universityRepository;

    public UniversityController(StudentService studentService,
                                UniversityRepository universityRepository) {
        this.studentService = studentService;
        this.universityRepository = universityRepository;
    }

    @GetMapping("/university/choose")
    public ModelAndView choose() {
        return this.view(this.universityRepository.findAll());
    }

    @PostMapping("/university/choose/{id}")
    public ModelAndView choose(@PathVariable Long id) throws Exception {
        this.studentService.chooseUniversity(id);
        return this.redirect("/overview");
    }
}
