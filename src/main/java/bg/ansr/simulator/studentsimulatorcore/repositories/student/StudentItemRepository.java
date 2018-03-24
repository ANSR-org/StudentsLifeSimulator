package bg.ansr.simulator.studentsimulatorcore.repositories.student;

import bg.ansr.simulator.studentsimulatorcore.entities.StudentItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentItemRepository extends JpaRepository<StudentItem, Long> {

    @Query(value = "SELECT * FROM student_item WHERE last_update < DATE_SUB(NOW(), INTERVAL 7 DAY)", nativeQuery = true)
    List<StudentItem> findItemsForScheduledUpdate();

}
