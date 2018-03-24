package bg.ansr.simulator.studentsimulatorcore.repositories.hostel;

import bg.ansr.simulator.studentsimulatorcore.entities.Hostel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostelRepository extends JpaRepository<Hostel, Long> {
}
