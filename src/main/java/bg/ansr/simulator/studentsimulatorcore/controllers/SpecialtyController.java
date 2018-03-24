package bg.ansr.simulator.studentsimulatorcore.controllers;

import bg.ansr.simulator.studentsimulatorcore.entities.Specialty;
import bg.ansr.simulator.studentsimulatorcore.entities.SpecialtyQuestion;
import bg.ansr.simulator.studentsimulatorcore.entities.Student;
import bg.ansr.simulator.studentsimulatorcore.models.specialty.ChooseSpecialtyBindingModel;
import bg.ansr.simulator.studentsimulatorcore.repositories.specialty.SpecialtyQuestionRepository;
import bg.ansr.simulator.studentsimulatorcore.repositories.specialty.SpecialtyRepository;
import bg.ansr.simulator.studentsimulatorcore.repositories.student.StudentRepository;
import bg.ansr.simulator.studentsimulatorcore.services.student.StudentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@PreAuthorize("isAuthenticated()")
public class SpecialtyController extends BaseController {

    private final StudentRepository studentRepository;
    private final StudentService studentService;
    private final SpecialtyRepository specialtyRepository;
    private final SpecialtyQuestionRepository questionRepository;


    public SpecialtyController(StudentRepository studentRepository,
                               StudentService studentService,
                               SpecialtyRepository specialtyRepository,
                               SpecialtyQuestionRepository questionRepository) {
        this.studentRepository = studentRepository;
        this.studentService = studentService;
        this.specialtyRepository = specialtyRepository;
        this.questionRepository = questionRepository;
    }

    @GetMapping("/specialties")
    public ModelAndView all() {
        return this.view(this.specialtyRepository.findAll());
    }

    @GetMapping("/specialties/{id}/test")
    public ModelAndView test(@PathVariable Long id) {
        return this.view(this.specialtyRepository.findOne(id).getQuestions());
    }

    @PostMapping("/specialties/{id}/test")
    public ModelAndView test(@PathVariable Long id, ChooseSpecialtyBindingModel model) throws Exception {
        Student student = this.studentService.current();
        if (student.getSpecialty() != null) {
            throw new Exception("Student already has specialty!");
        }

        Specialty specialty = this.specialtyRepository.findOne(id);
        Map<Long, String> answers = model.getAnswers();
        for (SpecialtyQuestion question : specialty.getQuestions()) {
            if (!answers.containsKey(question.getId())) {
                throw new Exception("Non full questions collection supplied. Probably user edited input!");
            }

            if (!question.getAnswer().trim().equals(answers.get(question.getId()).trim())) {
                return this.view();
            }
        }

        student.setSpecialty(specialty);
        this.studentRepository.save(student);
        specialty.getStudents().add(student);
        this.specialtyRepository.save(specialty);

        return this.redirect("/hostels/choose");
    }
}
