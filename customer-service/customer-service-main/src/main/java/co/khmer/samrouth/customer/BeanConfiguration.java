package co.khmer.samrouth.customer;

import co.khmer.samrouth.ecommerce.customer.service.CustomerDomainService;
import co.khmer.samrouth.ecommerce.customer.service.CustomerDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public CustomerDomainService customerDomainService() {
        return new CustomerDomainServiceImpl();
    }
}
