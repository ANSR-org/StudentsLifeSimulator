package bg.ansr.simulator.studentsimulatorcore.controllers;

import bg.ansr.simulator.studentsimulatorcore.models.student.UserRegisterBindingModel;
import bg.ansr.simulator.studentsimulatorcore.services.student.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StudentsController extends BaseController {

    private final StudentService studentService;

    public StudentsController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public ModelAndView index() {
        return this.view();
    }

    @GetMapping("/login")
    public String login() {
        return "main/login";
    }

    @GetMapping("/logout")
    public ModelAndView logout() {
        return this.view();
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
}
