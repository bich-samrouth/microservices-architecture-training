package co.khmer.samrouth.ecommerce.customer.usecase;

import co.khmer.samrouth.ecommerce.customer.entity.Customer;
import co.khmer.samrouth.ecommerce.customer.exception.CustomerNotFoundException;
import co.khmer.samrouth.ecommerce.customer.port.output.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class FindCustomerByIdUseCase {

    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public Customer execute(UUID customerId) {
        log.info("Finding customer by ID: {}", customerId);
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Could not find customer with ID: " + customerId));
    }
}
