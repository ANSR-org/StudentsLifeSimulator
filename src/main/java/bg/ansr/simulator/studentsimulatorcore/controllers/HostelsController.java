package bg.ansr.simulator.studentsimulatorcore.controllers;

import bg.ansr.simulator.studentsimulatorcore.repositories.hostel.HostelRepository;
import bg.ansr.simulator.studentsimulatorcore.repositories.student.StudentRepository;
import bg.ansr.simulator.studentsimulatorcore.services.student.StudentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@PreAuthorize("isAuthenticated()")
public class HostelsController extends BaseController {

    private final HostelRepository hostelRepository;
    private final StudentRepository studentRepository;
    private final StudentService studentService;

    public HostelsController(HostelRepository hostelRepository,
                             StudentRepository studentRepository,
                             StudentService studentService) {
        this.hostelRepository = hostelRepository;
        this.studentRepository = studentRepository;
        this.studentService = studentService;
    }

    @GetMapping("/hostels/choose")
    public ModelAndView choose() {
        return this.view(this.hostelRepository.findAll());
    }

    @PostMapping("/hostels/choose/{id}")
    public ModelAndView choose(@PathVariable Long id) throws Exception {
        this.studentService.chooseHostel(id);
        return this.redirect("/overview");
    }
}
