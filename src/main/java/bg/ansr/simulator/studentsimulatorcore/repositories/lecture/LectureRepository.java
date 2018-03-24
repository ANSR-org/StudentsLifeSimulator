package bg.ansr.simulator.studentsimulatorcore.repositories.lecture;

import bg.ansr.simulator.studentsimulatorcore.entities.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {

    List<Lecture> findAllBySpecialtyId(Long id);

    List<Lecture> findAllBySpecialtyIdAndMandatoryFalse(Long id);
}
