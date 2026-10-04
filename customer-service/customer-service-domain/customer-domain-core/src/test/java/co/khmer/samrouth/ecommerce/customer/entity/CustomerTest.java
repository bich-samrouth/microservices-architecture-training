package co.khmer.samrouth.ecommerce.customer.entity;

import co.khmer.samrouth.ecommerce.customer.exception.CustomerDomainException;
import co.khmer.samrouth.ecommerce.customer.service.CustomerDomainService;
import co.khmer.samrouth.ecommerce.customer.service.CustomerDomainServiceImpl;
import co.khmer.samrouth.ecommerce.order.valueobject.CustomerStatus;
import co.khmer.samrouth.ecommerce.order.valueobject.Email;
import co.khmer.samrouth.ecommerce.order.valueobject.LoyaltyTier;
import co.khmer.samrouth.ecommerce.order.valueobject.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    private CustomerDomainService customerDomainService;

    @BeforeEach
    void setUp() {
        customerDomainService = new CustomerDomainServiceImpl();
    }

    @Test
    void testInitializeAndValidateCustomer_Success() {
        Customer customer = Customer.builder()
                .username("john_doe")
                .familyName("Doe")
                .givenName("John")
                .email(new Email("john.doe@example.com"))
                .phoneNumber(new PhoneNumber("+123456789"))
                .build();

        customerDomainService.validateAndInitiateCustomer(customer);

        assertNotNull(customer.getId());
        assertNotNull(customer.getId().value());
        assertEquals(CustomerStatus.ACTIVE, customer.getStatus());
        assertEquals(LoyaltyTier.BRONZE, customer.getLoyaltyTier());
        assertEquals("john_doe", customer.getUsername());
        assertEquals("Doe", customer.getFamilyName());
        assertEquals("John", customer.getGivenName());
        assertEquals("john.doe@example.com", customer.getEmail().value());
        assertEquals("+123456789", customer.getPhoneNumber().number());
    }

    @Test
    void testValidateCustomer_MissingUsername_ThrowsException() {
        Customer customer = Customer.builder()
                .familyName("Doe")
                .givenName("John")
                .email(new Email("john.doe@example.com"))
                .build();

        assertThrows(CustomerDomainException.class, () -> customerDomainService.validateAndInitiateCustomer(customer));
    }

    @Test
    void testUpdateCustomerDetails_Success() {
        Customer customer = Customer.builder()
                .username("john_doe")
                .familyName("Doe")
                .givenName("John")
                .email(new Email("john.doe@example.com"))
                .phoneNumber(new PhoneNumber("+123456789"))
                .build();

        customerDomainService.validateAndInitiateCustomer(customer);

        customerDomainService.updateCustomer(
                customer,
                "Smith",
                "Johnny",
                new Email("johnny.smith@example.com"),
                new PhoneNumber("+987654321")
        );

        assertEquals("Smith", customer.getFamilyName());
        assertEquals("Johnny", customer.getGivenName());
        assertEquals("johnny.smith@example.com", customer.getEmail().value());
        assertEquals("+987654321", customer.getPhoneNumber().number());
    }

    @Test
    void testDeactivateAndActivateCustomer_Success() {
        Customer customer = Customer.builder()
                .username("john_doe")
                .familyName("Doe")
                .givenName("John")
                .email(new Email("john.doe@example.com"))
                .build();

        customerDomainService.validateAndInitiateCustomer(customer);
        assertEquals(CustomerStatus.ACTIVE, customer.getStatus());

        customerDomainService.deactivateCustomer(customer);
        assertEquals(CustomerStatus.INACTIVE, customer.getStatus());

        assertThrows(CustomerDomainException.class, () -> customerDomainService.deactivateCustomer(customer));

        customerDomainService.activateCustomer(customer);
        assertEquals(CustomerStatus.ACTIVE, customer.getStatus());
    }
}
