package co.khmer.samrouth.payment;

import co.khmer.samrouth.ecommerce.payment.service.PaymentDomainService;
import co.khmer.samrouth.ecommerce.payment.service.PaymentDomainServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public PaymentDomainService paymentDomainService() {
        return new PaymentDomainServiceImp();
    }
}
