package bg.ansr.simulator.studentsimulatorcore.repositories.specialty;

import bg.ansr.simulator.studentsimulatorcore.entities.SpecialtyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialtyQuestionRepository extends JpaRepository<SpecialtyQuestion, Long> {
}
