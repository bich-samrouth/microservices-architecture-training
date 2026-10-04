package co.khmer.samrouth.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {
        "co.khmer.samrouth.ecommerce.customer.persistence.entity"
})
@EnableJpaRepositories(basePackages = {
        "co.khmer.samrouth.ecommerce.customer.persistence.repository"
})
@SpringBootApplication(scanBasePackages = {
        "co.khmer.samrouth"
})
@EnableDiscoveryClient
public class CustomerServiceApplication {
    static void main(String[] args){
        SpringApplication.run(CustomerServiceApplication.class,args);
    }
}
