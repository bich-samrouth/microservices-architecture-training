package co.khmer.samrouth.ecommerce.customer.usecase;

import co.khmer.samrouth.ecommerce.customer.client.dto.UserResponse;
import co.khmer.samrouth.ecommerce.customer.client.exception.UserServiceUnavailableException;
import co.khmer.samrouth.ecommerce.customer.client.feign.UserFeignClient;
import co.khmer.samrouth.ecommerce.customer.dto.CreateCustomerCommand;
import co.khmer.samrouth.ecommerce.customer.dto.CreateCustomerResult;
import co.khmer.samrouth.ecommerce.customer.entity.Customer;
import co.khmer.samrouth.ecommerce.customer.event.CustomerCreatedEvent;
import co.khmer.samrouth.ecommerce.customer.exception.CustomerAlreadyExistsException;
import co.khmer.samrouth.ecommerce.customer.exception.CustomerDomainException;
import co.khmer.samrouth.ecommerce.customer.mapper.CustomerDomainMapper;
import co.khmer.samrouth.ecommerce.customer.port.output.CustomerRepository;
import co.khmer.samrouth.ecommerce.customer.service.CustomerDomainService;
import feign.FeignException;
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
    private final UserFeignClient userFeignClient;

    @Transactional
    public CreateCustomerResult execute(CreateCustomerCommand createCustomerCommand) {

        try {
            UserResponse userResponse = userFeignClient.getById(createCustomerCommand.userId());
            log.info("User response: {}", userResponse);
        } catch (UserServiceUnavailableException e) {
            throw new CustomerDomainException(e.getMessage());
        }

        if (customerRepository.existsByUsername(createCustomerCommand.username())) {
            throw new CustomerAlreadyExistsException("Customer with username '" + createCustomerCommand.username() + "' already exists!");
        }

        if (customerRepository.existsByEmail(createCustomerCommand.email())) {
            throw new CustomerAlreadyExistsException("Customer with email '" + createCustomerCommand.email() + "' already exists!");
        }

        Customer customer = customerDomainMapper.createCustomerCommandToCustomer(createCustomerCommand);
        CustomerCreatedEvent customerCreatedEvent = customerDomainService.validateAndInitiateCustomer(customer);

        Customer savedCustomer = customerRepository.save(customer);
        if (savedCustomer == null) {
            throw new CustomerDomainException("Could not save customer into database!");
        }

        return new CreateCustomerResult(savedCustomer.getId().value());
    }
}
