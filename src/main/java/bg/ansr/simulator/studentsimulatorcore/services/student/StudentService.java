package bg.ansr.simulator.studentsimulatorcore.services.student;

import bg.ansr.simulator.studentsimulatorcore.entities.Student;
import bg.ansr.simulator.studentsimulatorcore.models.lecture.ChosenOptionalLectureWrapper;
import bg.ansr.simulator.studentsimulatorcore.models.student.UserRegisterBindingModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface StudentService extends UserDetailsService {
    Student register(UserRegisterBindingModel model);

    void save(Student student);

    Student current();

    void chooseHostel(Long hostelId) throws Exception;

    void chooseOptionalLectures(ChosenOptionalLectureWrapper chosenLectures);

    void chooseUniversity(Long id) throws Exception;
}
