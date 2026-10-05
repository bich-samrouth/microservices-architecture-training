package co.khmer.samrouth.ecommerce.business.config;

import co.khmer.samrouth.ecommerce.business.service.BusinessDomainService;
import co.khmer.samrouth.ecommerce.business.service.BusinessDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public BusinessDomainService businessDomainService() {
        return new BusinessDomainServiceImpl();
    }
}