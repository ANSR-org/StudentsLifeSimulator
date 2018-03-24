package bg.ansr.simulator.studentsimulatorcore.repositories.schedule;

import bg.ansr.simulator.studentsimulatorcore.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
