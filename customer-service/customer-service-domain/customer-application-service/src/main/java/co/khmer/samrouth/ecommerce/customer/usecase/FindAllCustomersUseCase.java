package co.khmer.samrouth.ecommerce.customer.usecase;

import co.khmer.samrouth.ecommerce.customer.entity.Customer;
import co.khmer.samrouth.ecommerce.customer.port.output.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class FindAllCustomersUseCase {

    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public List<Customer> execute() {
        log.info("Finding all customers");
        return customerRepository.findAll();
    }
}
