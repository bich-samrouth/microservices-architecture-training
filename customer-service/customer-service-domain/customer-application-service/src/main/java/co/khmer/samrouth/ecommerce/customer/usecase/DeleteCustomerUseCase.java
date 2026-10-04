package co.khmer.samrouth.ecommerce.customer.usecase;

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
public class DeleteCustomerUseCase {

    private final CustomerRepository customerRepository;

    @Transactional
    public void execute(UUID customerId) {
        log.info("Deleting customer with ID: {}", customerId);

        if (!customerRepository.existsById(customerId)) {
            throw new CustomerNotFoundException("Could not find customer with ID: " + customerId);
        }

        customerRepository.deleteById(customerId);
        log.info("Customer with ID: {} deleted successfully", customerId);
    }
}
