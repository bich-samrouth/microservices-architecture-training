package co.khmer.samrouth.ecommerce.customer.restapi.controller;

import co.khmer.samrouth.ecommerce.customer.dto.CreateCustomerCommand;
import co.khmer.samrouth.ecommerce.customer.dto.CreateCustomerResult;
import co.khmer.samrouth.ecommerce.customer.dto.UpdateCustomerCommand;
import co.khmer.samrouth.ecommerce.customer.dto.UpdateCustomerResult;
import co.khmer.samrouth.ecommerce.customer.entity.Customer;
import co.khmer.samrouth.ecommerce.customer.restapi.dto.CustomerCreateRequest;
import co.khmer.samrouth.ecommerce.customer.restapi.dto.CustomerCreateResponse;
import co.khmer.samrouth.ecommerce.customer.restapi.dto.CustomerResponse;
import co.khmer.samrouth.ecommerce.customer.restapi.dto.CustomerUpdateRequest;
import co.khmer.samrouth.ecommerce.customer.restapi.dto.CustomerUpdateResponse;
import co.khmer.samrouth.ecommerce.customer.restapi.mapper.CustomerWebMapper;
import co.khmer.samrouth.ecommerce.customer.usecase.CreateCustomerUseCase;
import co.khmer.samrouth.ecommerce.customer.usecase.DeactivateCustomerUseCase;
import co.khmer.samrouth.ecommerce.customer.usecase.DeleteCustomerUseCase;
import co.khmer.samrouth.ecommerce.customer.usecase.FindAllCustomersUseCase;
import co.khmer.samrouth.ecommerce.customer.usecase.FindCustomerByIdUseCase;
import co.khmer.samrouth.ecommerce.customer.usecase.UpdateCustomerUseCase;
import co.khmer.samrouth.ecommerce.order.valueobject.CustomerId;
import co.khmer.samrouth.ecommerce.order.valueobject.CustomerStatus;
import co.khmer.samrouth.ecommerce.order.valueobject.Email;
import co.khmer.samrouth.ecommerce.order.valueobject.LoyaltyTier;
import co.khmer.samrouth.ecommerce.order.valueobject.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CreateCustomerUseCase createCustomerUseCase;
    @Mock
    private FindCustomerByIdUseCase findCustomerByIdUseCase;
    @Mock
    private FindAllCustomersUseCase findAllCustomersUseCase;
    @Mock
    private UpdateCustomerUseCase updateCustomerUseCase;
    @Mock
    private DeleteCustomerUseCase deleteCustomerUseCase;
    @Mock
    private DeactivateCustomerUseCase deactivateCustomerUseCase;
    @Mock
    private CustomerWebMapper customerWebMapper;

    @InjectMocks
    private CustomerController customerController;

    private UUID customerId;
    private Customer customer;

    @BeforeEach
    void setUp() {
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
        CustomerCreateRequest request = new CustomerCreateRequest("john_doe", "Doe", "John", "john.doe@example.com", "+123456789");
        CreateCustomerCommand command = new CreateCustomerCommand("john_doe", "Doe", "John", "john.doe@example.com", "+123456789");
        CreateCustomerResult result = new CreateCustomerResult(customerId);
        CustomerCreateResponse response = new CustomerCreateResponse(customerId);

        when(customerWebMapper.customerCreateRequestToCreateCustomerCommand(request)).thenReturn(command);
        when(createCustomerUseCase.execute(command)).thenReturn(result);
        when(customerWebMapper.createCustomerResultToCustomerCreateResponse(result)).thenReturn(response);

        CustomerCreateResponse actualResponse = customerController.createCustomer(request);

        assertNotNull(actualResponse);
        assertEquals(customerId, actualResponse.customerId());
    }

    @Test
    void testGetCustomerById_Success() {
        CustomerResponse response = new CustomerResponse(customerId, "john_doe", "Doe", "John", "john.doe@example.com", "+123456789", LoyaltyTier.BRONZE, CustomerStatus.ACTIVE);

        when(findCustomerByIdUseCase.execute(customerId)).thenReturn(customer);
        when(customerWebMapper.customerToCustomerResponse(customer)).thenReturn(response);

        CustomerResponse actualResponse = customerController.getCustomerById(customerId);

        assertNotNull(actualResponse);
        assertEquals(customerId, actualResponse.id());
        assertEquals("john_doe", actualResponse.username());
    }

    @Test
    void testGetAllCustomers_Success() {
        CustomerResponse response = new CustomerResponse(customerId, "john_doe", "Doe", "John", "john.doe@example.com", "+123456789", LoyaltyTier.BRONZE, CustomerStatus.ACTIVE);

        when(findAllCustomersUseCase.execute()).thenReturn(List.of(customer));
        when(customerWebMapper.customerListToCustomerResponseList(List.of(customer))).thenReturn(List.of(response));

        List<CustomerResponse> actualList = customerController.getAllCustomers();

        assertEquals(1, actualList.size());
        assertEquals("john_doe", actualList.get(0).username());
    }

    @Test
    void testUpdateCustomer_Success() {
        CustomerUpdateRequest request = new CustomerUpdateRequest("Smith", "Johnny", "johnny.smith@example.com", "+987654321");
        UpdateCustomerCommand command = new UpdateCustomerCommand(customerId, "Smith", "Johnny", "johnny.smith@example.com", "+987654321");
        UpdateCustomerResult result = new UpdateCustomerResult(customerId);
        CustomerUpdateResponse response = new CustomerUpdateResponse(customerId);

        when(customerWebMapper.customerUpdateRequestToUpdateCustomerCommand(customerId, request)).thenReturn(command);
        when(updateCustomerUseCase.execute(command)).thenReturn(result);
        when(customerWebMapper.updateCustomerResultToCustomerUpdateResponse(result)).thenReturn(response);

        CustomerUpdateResponse actualResponse = customerController.updateCustomer(customerId, request);

        assertNotNull(actualResponse);
        assertEquals(customerId, actualResponse.customerId());
    }

    @Test
    void testDeleteCustomer_Success() {
        customerController.deleteCustomer(customerId);

        verify(deleteCustomerUseCase, times(1)).execute(customerId);
    }

    @Test
    void testDeactivateCustomer_Success() {
        customerController.deactivateCustomer(customerId);

        verify(deactivateCustomerUseCase, times(1)).execute(any());
    }
}
