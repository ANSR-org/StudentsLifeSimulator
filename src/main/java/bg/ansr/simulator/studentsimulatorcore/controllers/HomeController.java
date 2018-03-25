package bg.ansr.simulator.studentsimulatorcore.controllers;

import bg.ansr.simulator.studentsimulatorcore.entities.Student;
import bg.ansr.simulator.studentsimulatorcore.models.student.UserRegisterBindingModel;
import bg.ansr.simulator.studentsimulatorcore.services.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class HomeController extends BaseController {

    private final StudentService studentService;

    @Autowired
    public HomeController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    private String landing() {
        return "main/index";
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

    @GetMapping("/home")
    private ModelAndView home(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (this.studentService == null) return this.redirect("/login");

        Student student = this.studentService.current();

        if (student.getUniversity() == null) {
            response.sendRedirect("/university/choose");
        } else if (student.getSpecialty() == null) {
            response.sendRedirect("/specialties");
        } else if (student.getHostel() == null) {
            response.sendRedirect("/hostels/choose");
        }
        request.getSession().setAttribute("energy", student.getEnergy());
        request.getSession().setAttribute("popularity", student.getPopularity());
        request.getSession().setAttribute("money", student.getMoney());
        return this.view();
    }
}
