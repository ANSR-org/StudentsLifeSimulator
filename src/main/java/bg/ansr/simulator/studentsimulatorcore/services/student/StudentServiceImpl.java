package bg.ansr.simulator.studentsimulatorcore.services.student;

import bg.ansr.simulator.studentsimulatorcore.entities.*;
import bg.ansr.simulator.studentsimulatorcore.models.lecture.ChosenOptionalLectureWrapper;
import bg.ansr.simulator.studentsimulatorcore.models.student.UserRegisterBindingModel;
import bg.ansr.simulator.studentsimulatorcore.repositories.hostel.HostelRepository;
import bg.ansr.simulator.studentsimulatorcore.repositories.lecture.LectureRepository;
import bg.ansr.simulator.studentsimulatorcore.repositories.schedule.ScheduleRepository;
import bg.ansr.simulator.studentsimulatorcore.repositories.student.ItemRepository;
import bg.ansr.simulator.studentsimulatorcore.repositories.student.StudentItemRepository;
import bg.ansr.simulator.studentsimulatorcore.repositories.student.StudentRepository;
import bg.ansr.simulator.studentsimulatorcore.repositories.university.UniversityRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashSet;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final HostelRepository hostelRepository;
    private final UniversityRepository universityRepository;
    private final ScheduleRepository scheduleRepository;
    private final LectureRepository lectureRepository;
    private final ItemRepository itemRepository;
    private final StudentItemRepository studentItemRepository;

    public StudentServiceImpl(StudentRepository studentRepository,
                              PasswordEncoder passwordEncoder,
                              HostelRepository hostelRepository,
                              UniversityRepository universityRepository,
                              ScheduleRepository scheduleRepository,
                              LectureRepository lectureRepository,
                              ItemRepository itemRepository,
                              StudentItemRepository studentItemRepository) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.hostelRepository = hostelRepository;
        this.universityRepository = universityRepository;
        this.scheduleRepository = scheduleRepository;
        this.lectureRepository = lectureRepository;
        this.itemRepository = itemRepository;
        this.studentItemRepository = studentItemRepository;
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
        student.setMoney(0L);
        student.setPopularity(0L);
        student.setEnergy(0L);
        return this.studentRepository.saveAndFlush(student);
    }

    @Override
    public Student current() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.studentRepository.findFirstByUsername(user.getUsername());
    }

    @Override
    public void chooseHostel(Long hostelId) throws Exception {
        Student student = this.current();
        if (student.getHostel() != null) {
            throw new Exception("Student already have hostel!");
        }

        Hostel hostel = this.hostelRepository.findOne(hostelId);

        if (hostel == null) {
            throw new Exception("Hostel does not exist!");
        }

        if (hostel.getRentPrice() > student.getMoney()) {
            throw new Exception("Student does not have enough money");
        }

        student.setHostel(hostel);
        student.setMoney(student.getMoney() - hostel.getRentPrice());
        this.studentRepository.save(student);
        hostel.getStudents().add(student);
        this.hostelRepository.save(hostel);
        for (Item item : this.itemRepository.findAll()) {
            StudentItem studentItem = new StudentItem();
            studentItem.setItem(item);
            studentItem.setCount(1L);
            studentItem.setStudent(student);
            studentItem.setLastUpdate(new Date());
            this.studentItemRepository.save(studentItem);
            student.getItems().add(studentItem);
            this.studentRepository.save(student);
            item.getStudentItems().add(studentItem);
            this.itemRepository.save(item);
        }
    }

    @Override
    public void chooseOptionalLectures(ChosenOptionalLectureWrapper chosenLectures) {
        //TODO check for collision
        chosenLectures.getChosenLectures().forEach(l -> {
            Student student = this.current();
            Lecture lecture = this.lectureRepository.findOne(l.getLectureId());
            Schedule schedule = new Schedule();
            schedule.setLecture(lecture);
            schedule.setSubscribedStudent(student);
            schedule.setStartedAt(l.getStartAt());
            schedule.setEndedAt(l.getEndAt());
            this.scheduleRepository.save(schedule);
            student.getSchedules().add(schedule);
            lecture.getSchedules().add(schedule);
            this.studentRepository.save(student);
            this.lectureRepository.save(lecture);
        });
    }

    @Override
    public void chooseUniversity(Long id) throws Exception {
        Student student = this.current();
        if (student.getUniversity() != null) {
            throw new Exception("Student already choose university!");
        }

        University university = this.universityRepository.findOne(id);

        if (university == null) {
            throw new Exception("University does not exist!");
        }

        university.getStudents().add(student);
        student.setUniversity(university);
        this.studentRepository.save(student);
        this.universityRepository.save(university);
    }

    @Override
    public void save(Student student){
        this.studentRepository.save(student);
    }
}
