package bg.ansr.simulator.studentsimulatorcore.repositories.student;

import bg.ansr.simulator.studentsimulatorcore.entities.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Trade, Long> {
}
