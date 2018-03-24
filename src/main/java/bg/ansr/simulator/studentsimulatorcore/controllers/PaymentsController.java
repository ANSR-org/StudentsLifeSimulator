package bg.ansr.simulator.studentsimulatorcore.controllers;

import bg.ansr.simulator.studentsimulatorcore.entities.Payment;
import bg.ansr.simulator.studentsimulatorcore.entities.PaymentPackage;
import bg.ansr.simulator.studentsimulatorcore.models.payment.PreparePaymentViewModel;
import bg.ansr.simulator.studentsimulatorcore.repositories.payment.PaymentPackageRepository;
import bg.ansr.simulator.studentsimulatorcore.repositories.payment.PaymentRepository;
import bg.ansr.simulator.studentsimulatorcore.services.student.StudentService;
import com.braintreegateway.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Controller
@PreAuthorize("isAuthenticated()")
public class PaymentsController extends BaseController {

    private Transaction.Status[] TRANSACTION_SUCCESS_STATUSES = new Transaction.Status[]{
            Transaction.Status.AUTHORIZED,
            Transaction.Status.AUTHORIZING,
            Transaction.Status.SETTLED,
            Transaction.Status.SETTLEMENT_CONFIRMED,
            Transaction.Status.SETTLEMENT_PENDING,
            Transaction.Status.SETTLING,
            Transaction.Status.SUBMITTED_FOR_SETTLEMENT
    };

    private final PaymentPackageRepository paymentPackageRepository;
    private final BraintreeGateway gateway;
    private final StudentService studentService;
    private final PaymentRepository paymentRepository;

    public PaymentsController(BraintreeGateway gateway, PaymentPackageRepository paymentPackageRepository, StudentService studentService, PaymentRepository paymentRepository) {
        this.gateway = gateway;
        this.paymentPackageRepository = paymentPackageRepository;
        this.studentService = studentService;
        this.paymentRepository = paymentRepository;
    }

    @GetMapping("/payments")
    public ModelAndView all() {
        return this.view(this.paymentPackageRepository.findAll());
    }

    @GetMapping("/payments/{id}")
    public ModelAndView request(@PathVariable Long id) {
        PreparePaymentViewModel viewModel = new PreparePaymentViewModel(
                this.paymentPackageRepository.findOne(id),
                this.gateway.clientToken().generate()
        );
        return this.view(viewModel);
    }

    @PostMapping("/payments/{id}")
    public ModelAndView process(@PathVariable Long id,
                                @RequestParam("amount") String amount,
                                @RequestParam("payment_method_nonce") String nonce,
                                RedirectAttributes attributes) throws Exception {
        BigDecimal decimalAmount;
        try {
            decimalAmount = new BigDecimal(amount);
        } catch (NumberFormatException e) {
            return this.redirect("");
        }
        PaymentPackage paymentPackage = this.paymentPackageRepository.findOne(id);
        if (decimalAmount.doubleValue() < paymentPackage.getUSD()) {
            throw new Exception("Wrong value");
        }

        TransactionRequest request = new TransactionRequest()
                .amount(decimalAmount)
                .paymentMethodNonce(nonce)
                .options()
                .submitForSettlement(true)
                .done();

        Result<Transaction> result = this.gateway.transaction().sale(request);

        String txId = null;
        if (result.isSuccess()) {
            txId = result.getTarget().getId();
        } else if (result.getTransaction() != null) {
            txId = result.getTransaction().getId();
        }

        if (txId != null) {
            attributes.addFlashAttribute("success", "Congratulations. You have bought your ingame money.");
            Payment payment = new Payment();
            payment.setStudent(this.studentService.current());
            payment.setPaymentPackage(paymentPackage);
            payment.setTransaction(txId);
            this.paymentRepository.save(payment);
        }

        attributes.addFlashAttribute("error", "Transaction was not successful");
        return this.redirect("");
    }

    @PostMapping("/payments/notify")
    public @ResponseBody Object notification(HttpServletRequest request) {
        WebhookNotification webhookNotification = gateway.webhookNotification().parse(
                request.getParameter("bt_signature"),
                request.getParameter("bt_payload")
        );

        System.out.println(webhookNotification.getTransaction().getAmount());
        System.out.println(webhookNotification.getTransaction().getId());
        Payment payment = this.paymentRepository.findFirstByTransaction(webhookNotification.getTransaction().getId());
        System.out.println(payment.getStudent().getUsername());
        System.out.println(payment.getPaymentPackage().getIngameMoney());
        System.out.println(payment.getPaymentPackage().getUSD());
        return null;
    }
}
