package bg.ansr.simulator.studentsimulatorcore.repositories.student;

import bg.ansr.simulator.studentsimulatorcore.entities.StudentItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentItemRepository extends JpaRepository<StudentItem, Long> {
}
