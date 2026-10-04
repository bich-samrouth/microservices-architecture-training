package co.khmer.samrouth.ecommerce.customer.usecase;

import co.khmer.samrouth.ecommerce.customer.dto.CreateCustomerCommand;
import co.khmer.samrouth.ecommerce.customer.dto.CreateCustomerResult;
import co.khmer.samrouth.ecommerce.customer.entity.Customer;
import co.khmer.samrouth.ecommerce.customer.event.CustomerCreatedEvent;
import co.khmer.samrouth.ecommerce.customer.exception.CustomerAlreadyExistsException;
import co.khmer.samrouth.ecommerce.customer.exception.CustomerDomainException;
import co.khmer.samrouth.ecommerce.customer.mapper.CustomerDomainMapper;
import co.khmer.samrouth.ecommerce.customer.port.output.CustomerRepository;
import co.khmer.samrouth.ecommerce.customer.service.CustomerDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreateCustomerUseCase {

    private final CustomerDomainService customerDomainService;
    private final CustomerRepository customerRepository;
    private final CustomerDomainMapper customerDomainMapper;

    @Transactional
    public CreateCustomerResult execute(CreateCustomerCommand createCustomerCommand) {
        log.info("Executing create customer use case for username: {}", createCustomerCommand.username());

        if (customerRepository.existsByUsername(createCustomerCommand.username())) {
            throw new CustomerAlreadyExistsException("Customer with username '" + createCustomerCommand.username() + "' already exists!");
        }

        if (customerRepository.existsByEmail(createCustomerCommand.email())) {
            throw new CustomerAlreadyExistsException("Customer with email '" + createCustomerCommand.email() + "' already exists!");
        }

        Customer customer = customerDomainMapper.createCustomerCommandToCustomer(createCustomerCommand);
        CustomerCreatedEvent customerCreatedEvent = customerDomainService.validateAndInitiateCustomer(customer);
        log.info("Customer initialized with ID: {}", customerCreatedEvent.getCustomer().getId().value());

        Customer savedCustomer = customerRepository.save(customer);
        if (savedCustomer == null) {
            throw new CustomerDomainException("Could not save customer into database!");
        }

        log.info("Customer created successfully with ID: {}", savedCustomer.getId().value());
        return new CreateCustomerResult(savedCustomer.getId().value());
    }
}
