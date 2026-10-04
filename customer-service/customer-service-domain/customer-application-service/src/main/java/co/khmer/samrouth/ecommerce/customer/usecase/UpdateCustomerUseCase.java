package co.khmer.samrouth.ecommerce.customer.usecase;

import co.khmer.samrouth.ecommerce.customer.dto.UpdateCustomerCommand;
import co.khmer.samrouth.ecommerce.customer.dto.UpdateCustomerResult;
import co.khmer.samrouth.ecommerce.customer.entity.Customer;
import co.khmer.samrouth.ecommerce.customer.exception.CustomerAlreadyExistsException;
import co.khmer.samrouth.ecommerce.customer.exception.CustomerDomainException;
import co.khmer.samrouth.ecommerce.customer.exception.CustomerNotFoundException;
import co.khmer.samrouth.ecommerce.customer.port.output.CustomerRepository;
import co.khmer.samrouth.ecommerce.customer.service.CustomerDomainService;
import co.khmer.samrouth.ecommerce.order.valueobject.Email;
import co.khmer.samrouth.ecommerce.order.valueobject.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class UpdateCustomerUseCase {

    private final CustomerDomainService customerDomainService;
    private final CustomerRepository customerRepository;

    @Transactional
    public UpdateCustomerResult execute(UpdateCustomerCommand updateCustomerCommand) {
        log.info("Executing update customer for ID: {}", updateCustomerCommand.customerId());

        Customer customer = customerRepository.findById(updateCustomerCommand.customerId())
                .orElseThrow(() -> new CustomerNotFoundException("Could not find customer with ID: " + updateCustomerCommand.customerId()));

        // Check email uniqueness if email is changed
        if (updateCustomerCommand.email() != null && !updateCustomerCommand.email().equals(customer.getEmail().value())) {
            Optional<Customer> existingCustomerWithEmail = customerRepository.findByEmail(updateCustomerCommand.email());
            if (existingCustomerWithEmail.isPresent() && !existingCustomerWithEmail.get().getId().equals(customer.getId())) {
                throw new CustomerAlreadyExistsException("Customer with email '" + updateCustomerCommand.email() + "' already exists!");
            }
        }

        customerDomainService.updateCustomer(
                customer,
                updateCustomerCommand.familyName(),
                updateCustomerCommand.givenName(),
                new Email(updateCustomerCommand.email()),
                new PhoneNumber(updateCustomerCommand.phoneNumber())
        );

        Customer savedCustomer = customerRepository.save(customer);
        if (savedCustomer == null) {
            throw new CustomerDomainException("Could not update customer into database!");
        }

        log.info("Customer with ID: {} updated successfully", savedCustomer.getId().value());
        return new UpdateCustomerResult(savedCustomer.getId().value());
    }
}
