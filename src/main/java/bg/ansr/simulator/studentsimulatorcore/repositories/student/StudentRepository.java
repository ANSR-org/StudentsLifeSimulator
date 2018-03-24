package bg.ansr.simulator.studentsimulatorcore.repositories.student;

import bg.ansr.simulator.studentsimulatorcore.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findFirstByUsername(String username);
}
