package bg.ansr.simulator.studentsimulatorcore.config;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BeanRegistrar {

    private final Environment environment;
    private final String merchantId;
    private final String publicKey;
    private final String privateKey;

    public BeanRegistrar(@Value("${paypal.environment}") String environment,
                         @Value("${paypal.merchantId}") String merchantId,
                         @Value("${paypal.publicKey}") String publicKey,
                         @Value("${paypal.privateKey}") String privateKey) {
        this.environment = environment.equals("sandbox") ? Environment.SANDBOX : Environment.PRODUCTION;
        this.merchantId = merchantId;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public BraintreeGateway braintreeGateway() {
        return new BraintreeGateway(
                this.environment,
                this.merchantId,
                this.publicKey,
                this.privateKey
        );
    }
}
