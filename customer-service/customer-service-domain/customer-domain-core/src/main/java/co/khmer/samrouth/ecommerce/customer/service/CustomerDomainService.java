package co.khmer.samrouth.ecommerce.customer.service;

import co.khmer.samrouth.ecommerce.customer.entity.Customer;
import co.khmer.samrouth.ecommerce.customer.event.CustomerCreatedEvent;
import co.khmer.samrouth.ecommerce.order.valueobject.Email;
import co.khmer.samrouth.ecommerce.order.valueobject.PhoneNumber;

public interface CustomerDomainService {
    CustomerCreatedEvent validateAndInitiateCustomer(Customer customer);
    void updateCustomer(Customer customer, String familyName, String givenName, Email email, PhoneNumber phoneNumber);
    void deactivateCustomer(Customer customer);
    void activateCustomer(Customer customer);
}
