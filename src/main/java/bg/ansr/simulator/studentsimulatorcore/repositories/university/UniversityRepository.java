package bg.ansr.simulator.studentsimulatorcore.repositories.university;

import bg.ansr.simulator.studentsimulatorcore.entities.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {
}
