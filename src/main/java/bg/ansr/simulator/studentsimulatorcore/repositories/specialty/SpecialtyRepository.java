package bg.ansr.simulator.studentsimulatorcore.repositories.specialty;

import bg.ansr.simulator.studentsimulatorcore.entities.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
}
