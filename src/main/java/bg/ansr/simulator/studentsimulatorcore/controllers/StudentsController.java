package bg.ansr.simulator.studentsimulatorcore.controllers;

import bg.ansr.simulator.studentsimulatorcore.models.student.UserRegisterBindingModel;
import bg.ansr.simulator.studentsimulatorcore.repositories.student.StudentRepository;
import bg.ansr.simulator.studentsimulatorcore.services.student.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class StudentsController extends BaseController {

    private final StudentService studentService;
    private final StudentRepository studentRepository;

    public StudentsController(StudentService studentService, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

    @GetMapping("/login")
    public String login() {
        return "main/login";
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return this.redirect("/login");
    }

    @GetMapping("/students/register")
    public String register() {
        return "main/register";
    }

    @PostMapping("/students/register")
    public ModelAndView register(UserRegisterBindingModel model) {
        try {
            this.studentService.register(model);
            return this.redirect("/login");
        } catch (Exception e) {
            e.printStackTrace();
            return this.view();
        }
    }

    @GetMapping("/rankings")
    public ModelAndView rankings() {
        return this.view(this.studentRepository.findAllByOrderByPointsDesc());
    }
}
