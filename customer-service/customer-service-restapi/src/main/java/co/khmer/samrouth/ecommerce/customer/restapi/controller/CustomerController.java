package co.khmer.samrouth.ecommerce.customer.restapi.controller;

import co.khmer.samrouth.ecommerce.customer.dto.CreateCustomerCommand;
import co.khmer.samrouth.ecommerce.customer.dto.CreateCustomerResult;
import co.khmer.samrouth.ecommerce.customer.dto.DeactivateCustomerCommand;
import co.khmer.samrouth.ecommerce.customer.entity.Customer;
import co.khmer.samrouth.ecommerce.customer.restapi.dto.CustomerCreateRequest;
import co.khmer.samrouth.ecommerce.customer.restapi.dto.CustomerCreateResponse;
import co.khmer.samrouth.ecommerce.customer.restapi.dto.CustomerResponse;
import co.khmer.samrouth.ecommerce.customer.restapi.mapper.CustomerWebMapper;
import co.khmer.samrouth.ecommerce.customer.usecase.CreateCustomerUseCase;
import co.khmer.samrouth.ecommerce.customer.usecase.DeactivateCustomerUseCase;
import co.khmer.samrouth.ecommerce.customer.usecase.DeleteCustomerUseCase;
import co.khmer.samrouth.ecommerce.customer.usecase.FindCustomerByIdUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
@Slf4j
@RequiredArgsConstructor
public class CustomerController {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final FindCustomerByIdUseCase findCustomerByIdUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;
    private final DeactivateCustomerUseCase deactivateCustomerUseCase;
    private final CustomerWebMapper customerWebMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerCreateResponse createCustomer(@Valid @RequestBody CustomerCreateRequest request) {
        log.info("Creating customer with username: {}", request.username());
        CreateCustomerCommand command = customerWebMapper.customerCreateRequestToCreateCustomerCommand(request);
        CreateCustomerResult result = createCustomerUseCase.execute(command);
        return customerWebMapper.createCustomerResultToCustomerCreateResponse(result);
    }

    @GetMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse getCustomerById(@PathVariable UUID customerId) {
        log.info("Retrieving customer by ID: {}", customerId);
        Customer customer = findCustomerByIdUseCase.execute(customerId);
        return customerWebMapper.customerToCustomerResponse(customer);
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable UUID customerId) {
        log.info("Deleting customer with ID: {}", customerId);
        deleteCustomerUseCase.execute(customerId);
    }

    @PatchMapping("/{customerId}/deactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateCustomer(@PathVariable UUID customerId) {
        log.info("Deactivating customer with ID: {}", customerId);
        deactivateCustomerUseCase.execute(new DeactivateCustomerCommand(customerId));
    }
}
