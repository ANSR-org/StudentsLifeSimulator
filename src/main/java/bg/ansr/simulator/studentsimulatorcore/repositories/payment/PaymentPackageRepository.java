package bg.ansr.simulator.studentsimulatorcore.repositories.payment;

import bg.ansr.simulator.studentsimulatorcore.entities.PaymentPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentPackageRepository extends JpaRepository<PaymentPackage, Long> {
}
