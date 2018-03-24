package bg.ansr.simulator.studentsimulatorcore.controllers;

import bg.ansr.simulator.studentsimulatorcore.entities.Specialty;
import bg.ansr.simulator.studentsimulatorcore.entities.SpecialtyQuestion;
import bg.ansr.simulator.studentsimulatorcore.entities.Student;
import bg.ansr.simulator.studentsimulatorcore.models.lecture.ChosenOptionalLectureWrapper;
import bg.ansr.simulator.studentsimulatorcore.models.specialty.ChooseSpecialtyBindingModel;
import bg.ansr.simulator.studentsimulatorcore.repositories.lecture.LectureRepository;
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
import java.util.Set;

@Controller
@PreAuthorize("isAuthenticated()")
public class SpecialtyController extends BaseController {

    private static final int START_MONEY = 1000;
    private static final int START_ENERGY = 50;


    private final StudentRepository studentRepository;
    private final StudentService studentService;
    private final SpecialtyRepository specialtyRepository;
    private final SpecialtyQuestionRepository questionRepository;
    private final LectureRepository lectureRepository;


    public SpecialtyController(StudentRepository studentRepository,
                               StudentService studentService,
                               SpecialtyRepository specialtyRepository,
                               SpecialtyQuestionRepository questionRepository,
                               LectureRepository lectureRepository) {
        this.studentRepository = studentRepository;
        this.studentService = studentService;
        this.specialtyRepository = specialtyRepository;
        this.questionRepository = questionRepository;
        this.lectureRepository = lectureRepository;
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
        Set<SpecialtyQuestion> questions = specialty.getQuestions();
        double size = questions.size();
        double answered = 0;
        for (SpecialtyQuestion question : questions) {
            if (!answers.containsKey(question.getId())) {
                throw new Exception("Non full questions collection supplied. Probably user edited input!");
            }

            if (question.getAnswer().trim().equals(answers.get(question.getId()).trim())) {
                answered++;
            }
        }

        double fraction = Math.max(0.1, size / answered);
        student.setMoney(student.getMoney() + (int) (START_MONEY * fraction));
        student.setEnergy(student.getEnergy() + (int) (START_ENERGY * fraction));
        student.setPopularity(0L);
        student.setSpecialty(specialty);
        this.studentRepository.save(student);
        specialty.getStudents().add(student);
        this.specialtyRepository.save(specialty);

        return this.redirect("/specialty/{id}/lectures/mandatory");
    }


    @GetMapping("/specialty/{id}/lectures/mandatory")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView mandatoryLectures(@PathVariable Long id) throws Exception {
        if (this.studentService.current().getSpecialty().getId().equals(id)) {
            return this.view(this.lectureRepository.findAllBySpecialtyId(id));
        }
        throw new Exception("Student is not enrolled for this specialty");
    }

    @GetMapping("/specialty/{id}/lectures/optional")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView optionalLectures(@PathVariable Long id) throws Exception {
        if (this.studentService.current().getSpecialty().getId().equals(id)) {
            return this.view(this.lectureRepository.findAllBySpecialtyIdAndMandatoryFalse(id));
        }
        throw new Exception("Student is not enrolled for this specialty");
    }


    @PostMapping("/specialty/{id}/lectures/mandatory")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView optionalLectures(@PathVariable Long id, ChosenOptionalLectureWrapper chosenLectures) throws Exception {
        if (this.studentService.current().getSpecialty().getId().equals(id)) {
            return this.redirect("/hostels/choose");
        }
        throw new Exception("Student is not enrolled for this specialty");
    }

}
