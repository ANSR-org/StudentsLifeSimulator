package bg.ansr.simulator.studentsimulatorcore.entities;

import javax.persistence.*;

@Entity
public class Payment {

    private Long id;
    private String transaction;
    private Student student;
    private PaymentPackage paymentPackage;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    @ManyToOne
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @ManyToOne
    public PaymentPackage getPaymentPackage() {
        return paymentPackage;
    }

    public void setPaymentPackage(PaymentPackage paymentPackage) {
        this.paymentPackage = paymentPackage;
    }
}
