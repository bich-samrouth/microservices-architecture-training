package co.khmer.samrouth.ecommerce.customer.usecase;

import co.khmer.samrouth.ecommerce.customer.dto.CreateCustomerCommand;
import co.khmer.samrouth.ecommerce.customer.dto.CreateCustomerResult;
import co.khmer.samrouth.ecommerce.customer.dto.DeactivateCustomerCommand;
import co.khmer.samrouth.ecommerce.customer.dto.UpdateCustomerCommand;
import co.khmer.samrouth.ecommerce.customer.dto.UpdateCustomerResult;
import co.khmer.samrouth.ecommerce.customer.entity.Customer;
import co.khmer.samrouth.ecommerce.customer.event.CustomerCreatedEvent;
import co.khmer.samrouth.ecommerce.customer.exception.CustomerAlreadyExistsException;
import co.khmer.samrouth.ecommerce.customer.exception.CustomerNotFoundException;
import co.khmer.samrouth.ecommerce.customer.mapper.CustomerDomainMapper;
import co.khmer.samrouth.ecommerce.customer.port.output.CustomerRepository;
import co.khmer.samrouth.ecommerce.customer.service.CustomerDomainService;
import co.khmer.samrouth.ecommerce.order.valueobject.CustomerId;
import co.khmer.samrouth.ecommerce.order.valueobject.CustomerStatus;
import co.khmer.samrouth.ecommerce.order.valueobject.Email;
import co.khmer.samrouth.ecommerce.order.valueobject.LoyaltyTier;
import co.khmer.samrouth.ecommerce.order.valueobject.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerUseCasesTest {

    @Mock
    private CustomerDomainService customerDomainService;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerDomainMapper customerDomainMapper;

    private CreateCustomerUseCase createCustomerUseCase;
    private FindCustomerByIdUseCase findCustomerByIdUseCase;
    private FindAllCustomersUseCase findAllCustomersUseCase;
    private UpdateCustomerUseCase updateCustomerUseCase;
    private DeleteCustomerUseCase deleteCustomerUseCase;
    private DeactivateCustomerUseCase deactivateCustomerUseCase;

    private UUID customerId;
    private Customer customer;

    @BeforeEach
    void setUp() {
        createCustomerUseCase = new CreateCustomerUseCase(customerDomainService, customerRepository, customerDomainMapper);
        findCustomerByIdUseCase = new FindCustomerByIdUseCase(customerRepository);
        findAllCustomersUseCase = new FindAllCustomersUseCase(customerRepository);
        updateCustomerUseCase = new UpdateCustomerUseCase(customerDomainService, customerRepository);
        deleteCustomerUseCase = new DeleteCustomerUseCase(customerRepository);
        deactivateCustomerUseCase = new DeactivateCustomerUseCase(customerDomainService, customerRepository);

        customerId = UUID.randomUUID();
        customer = Customer.builder()
                .id(new CustomerId(customerId))
                .username("john_doe")
                .familyName("Doe")
                .givenName("John")
                .email(new Email("john.doe@example.com"))
                .phoneNumber(new PhoneNumber("+123456789"))
                .loyaltyTier(LoyaltyTier.BRONZE)
                .status(CustomerStatus.ACTIVE)
                .build();
    }

    @Test
    void testCreateCustomer_Success() {
        CreateCustomerCommand command = new CreateCustomerCommand("john_doe", "Doe", "John", "john.doe@example.com", "+123456789");

        when(customerRepository.existsByUsername("john_doe")).thenReturn(false);
        when(customerRepository.existsByEmail("john.doe@example.com")).thenReturn(false);
        when(customerDomainMapper.createCustomerCommandToCustomer(command)).thenReturn(customer);
        when(customerDomainService.validateAndInitiateCustomer(customer)).thenReturn(new CustomerCreatedEvent(customer, ZonedDateTime.now()));
        when(customerRepository.save(customer)).thenReturn(customer);

        CreateCustomerResult result = createCustomerUseCase.execute(command);

        assertNotNull(result);
        assertEquals(customerId, result.customerId());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testCreateCustomer_DuplicateUsername_ThrowsException() {
        CreateCustomerCommand command = new CreateCustomerCommand("john_doe", "Doe", "John", "john.doe@example.com", "+123456789");

        when(customerRepository.existsByUsername("john_doe")).thenReturn(true);

        assertThrows(CustomerAlreadyExistsException.class, () -> createCustomerUseCase.execute(command));
        verify(customerRepository, never()).save(any());
    }

    @Test
    void testFindCustomerById_Success() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        Customer foundCustomer = findCustomerByIdUseCase.execute(customerId);

        assertNotNull(foundCustomer);
        assertEquals(customerId, foundCustomer.getId().value());
    }

    @Test
    void testFindCustomerById_NotFound_ThrowsException() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> findCustomerByIdUseCase.execute(customerId));
    }

    @Test
    void testFindAllCustomers_Success() {
        when(customerRepository.findAll()).thenReturn(List.of(customer));

        List<Customer> customers = findAllCustomersUseCase.execute();

        assertEquals(1, customers.size());
        assertEquals("john_doe", customers.get(0).getUsername());
    }

    @Test
    void testUpdateCustomer_Success() {
        UpdateCustomerCommand command = new UpdateCustomerCommand(customerId, "Smith", "Johnny", "johnny.smith@example.com", "+987654321");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerRepository.findByEmail("johnny.smith@example.com")).thenReturn(Optional.empty());
        when(customerRepository.save(customer)).thenReturn(customer);

        UpdateCustomerResult result = updateCustomerUseCase.execute(command);

        assertNotNull(result);
        assertEquals(customerId, result.customerId());
        verify(customerDomainService).updateCustomer(eq(customer), eq("Smith"), eq("Johnny"), any(Email.class), any(PhoneNumber.class));
        verify(customerRepository).save(customer);
    }

    @Test
    void testDeleteCustomer_Success() {
        when(customerRepository.existsById(customerId)).thenReturn(true);

        deleteCustomerUseCase.execute(customerId);

        verify(customerRepository, times(1)).deleteById(customerId);
    }

    @Test
    void testDeleteCustomer_NotFound_ThrowsException() {
        when(customerRepository.existsById(customerId)).thenReturn(false);

        assertThrows(CustomerNotFoundException.class, () -> deleteCustomerUseCase.execute(customerId));
        verify(customerRepository, never()).deleteById(any());
    }

    @Test
    void testDeactivateCustomer_Success() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);

        deactivateCustomerUseCase.execute(new DeactivateCustomerCommand(customerId));

        verify(customerDomainService).deactivateCustomer(customer);
        verify(customerRepository).save(customer);
    }
}
