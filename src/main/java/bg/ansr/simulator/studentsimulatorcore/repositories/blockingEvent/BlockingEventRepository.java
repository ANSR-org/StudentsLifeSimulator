package bg.ansr.simulator.studentsimulatorcore.repositories.blockingEvent;

import bg.ansr.simulator.studentsimulatorcore.entities.BlockingEvent;
import bg.ansr.simulator.studentsimulatorcore.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface BlockingEventRepository extends JpaRepository<BlockingEvent, Long> {

    int countAllByStudentAndStartedAtAfter(Student student, LocalDateTime startedAt);

    BlockingEvent findFirstByStudentAndStartedAtBeforeAndEndedAtAfterOrderByStartedAtDesc(Student student,
                                                                                          LocalDateTime startedAt,
                                                                                          LocalDateTime endedAt);
}
