package co.khmer.samrouth.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {
        "co.khmer.samrouth.ecommerce.order.persistence.entity"
})
@EnableJpaRepositories(basePackages = {
        "co.khmer.samrouth.ecommerce.order.persistence.repository"
})
@SpringBootApplication(scanBasePackages = {
        "co.khmer.samrouth"
})
@EnableDiscoveryClient
public class OrderServiceApplication {
    static void main(String[] args){
        SpringApplication.run(OrderServiceApplication.class,args);
    }
}
