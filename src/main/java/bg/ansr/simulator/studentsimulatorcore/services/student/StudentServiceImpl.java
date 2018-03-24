package bg.ansr.simulator.studentsimulatorcore.services.student;

import bg.ansr.simulator.studentsimulatorcore.entities.Hostel;
import bg.ansr.simulator.studentsimulatorcore.entities.Student;
import bg.ansr.simulator.studentsimulatorcore.models.student.UserRegisterBindingModel;
import bg.ansr.simulator.studentsimulatorcore.repositories.hostel.HostelRepository;
import bg.ansr.simulator.studentsimulatorcore.repositories.student.StudentRepository;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final HostelRepository hostelRepository;

    public StudentServiceImpl(StudentRepository studentRepository,
                              PasswordEncoder passwordEncoder,
                              HostelRepository hostelRepository) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.hostelRepository = hostelRepository;
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

    @Override
    public Student current() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.studentRepository.findFirstByUsername(user.getUsername());
    }

    @Override
    public void chooseHostel(Long hostelId) throws Exception {
        Student student = this.current();
        if (student.getHostel() != null) {
            throw new Exception("User already have hostel!");
        }

        Hostel hostel = this.hostelRepository.findOne(hostelId);

        if (hostel == null) {
            throw new Exception("Hostel does not exist!");
        }

        student.setHostel(hostel);
        this.studentRepository.save(student);
        hostel.getStudents().add(student);
        this.hostelRepository.save(hostel);
    }
}
