package bg.ansr.simulator.studentsimulatorcore.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class PaymentPackage {

    private Long id;
    private double USD;
    private double ingameMoney;
    private Set<Payment> payments;

    public PaymentPackage() {
        this.payments = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getUSD() {
        return USD;
    }

    public void setUSD(double USD) {
        this.USD = USD;
    }

    public double getIngameMoney() {
        return ingameMoney;
    }

    public void setIngameMoney(double ingameMoney) {
        this.ingameMoney = ingameMoney;
    }

    @OneToMany(targetEntity = Payment.class, mappedBy = "paymentPackage")
    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }
}
