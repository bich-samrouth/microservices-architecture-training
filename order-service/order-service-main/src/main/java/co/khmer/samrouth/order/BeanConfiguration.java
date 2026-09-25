package co.khmer.samrouth.order;

import co.khmer.samrouth.domain.service.OrderDomainService;
import co.khmer.samrouth.domain.service.OrderDomainServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// How to configure bean
// 1.Annotation based
// 2.Java Base (method)
@Configuration
public class BeanConfiguration {

    @Bean
    public OrderDomainService orderDomainService(){
        return new OrderDomainServiceImp();
    }
}
