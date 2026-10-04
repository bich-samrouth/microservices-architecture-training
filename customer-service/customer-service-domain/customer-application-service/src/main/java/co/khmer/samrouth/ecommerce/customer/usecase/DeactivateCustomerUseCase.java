package co.khmer.samrouth.ecommerce.customer.usecase;

import co.khmer.samrouth.ecommerce.customer.dto.DeactivateCustomerCommand;
import co.khmer.samrouth.ecommerce.customer.entity.Customer;
import co.khmer.samrouth.ecommerce.customer.exception.CustomerDomainException;
import co.khmer.samrouth.ecommerce.customer.exception.CustomerNotFoundException;
import co.khmer.samrouth.ecommerce.customer.port.output.CustomerRepository;
import co.khmer.samrouth.ecommerce.customer.service.CustomerDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeactivateCustomerUseCase {

    private final CustomerDomainService customerDomainService;
    private final CustomerRepository customerRepository;

    @Transactional
    public void execute(DeactivateCustomerCommand deactivateCustomerCommand) {
        log.info("Deactivating customer with ID: {}", deactivateCustomerCommand.customerId());

        Customer customer = customerRepository.findById(deactivateCustomerCommand.customerId())
                .orElseThrow(() -> new CustomerNotFoundException("Could not find customer with ID: " + deactivateCustomerCommand.customerId()));

        customerDomainService.deactivateCustomer(customer);

        Customer savedCustomer = customerRepository.save(customer);
        if (savedCustomer == null) {
            throw new CustomerDomainException("Could not deactivate customer in database!");
        }

        log.info("Customer with ID: {} deactivated successfully", savedCustomer.getId().value());
    }
}
