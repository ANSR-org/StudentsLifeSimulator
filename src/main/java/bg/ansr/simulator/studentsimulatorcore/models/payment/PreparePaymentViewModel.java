package bg.ansr.simulator.studentsimulatorcore.models.payment;

import bg.ansr.simulator.studentsimulatorcore.entities.PaymentPackage;

public class PreparePaymentViewModel {

    private PaymentPackage paymentPackage;
    private String clientToken;

    public PreparePaymentViewModel() {

    }

    public PreparePaymentViewModel(PaymentPackage paymentPackage, String clientToken) {
        this.paymentPackage = paymentPackage;
        this.clientToken = clientToken;
    }

    public PaymentPackage getPaymentPackage() {
        return paymentPackage;
    }

    public void setPaymentPackage(PaymentPackage paymentPackage) {
        this.paymentPackage = paymentPackage;
    }

    public String getClientToken() {
        return clientToken;
    }

    public void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }
}
