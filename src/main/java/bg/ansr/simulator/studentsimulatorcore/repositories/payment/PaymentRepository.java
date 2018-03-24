package bg.ansr.simulator.studentsimulatorcore.repositories.payment;

import bg.ansr.simulator.studentsimulatorcore.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findFirstByTransaction(String transaction);
}
