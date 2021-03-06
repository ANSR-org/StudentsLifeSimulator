package bg.ansr.simulator.studentsimulatorcore.repositories.student;

import bg.ansr.simulator.studentsimulatorcore.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
