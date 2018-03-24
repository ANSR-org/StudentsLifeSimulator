package bg.ansr.simulator.studentsimulatorcore.services.student;

import bg.ansr.simulator.studentsimulatorcore.entities.Student;
import bg.ansr.simulator.studentsimulatorcore.models.student.UserRegisterBindingModel;
import bg.ansr.simulator.studentsimulatorcore.repositories.student.StudentRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentServiceImpl(StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = this.studentRepository.findFirstByUsername(username);
        if (student == null) {
            throw new UsernameNotFoundException("Username not found");
        }

        return new User(student.getUsername(), student.getPassword(), new HashSet<>());
    }

    @Override
    public Student register(UserRegisterBindingModel model) {
        Student student = new Student();
        student.setPassword(this.passwordEncoder.encode(model.getPassword()));
        student.setUsername(model.getUsername());

        return this.studentRepository.saveAndFlush(student);
    }
}
