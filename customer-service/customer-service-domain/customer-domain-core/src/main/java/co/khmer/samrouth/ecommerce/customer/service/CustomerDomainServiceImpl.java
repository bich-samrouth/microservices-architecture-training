package co.khmer.samrouth.ecommerce.customer.service;

import co.khmer.samrouth.ecommerce.customer.entity.Customer;
import co.khmer.samrouth.ecommerce.customer.event.CustomerCreatedEvent;
import co.khmer.samrouth.ecommerce.order.valueobject.Email;
import co.khmer.samrouth.ecommerce.order.valueobject.PhoneNumber;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
public class CustomerDomainServiceImpl implements CustomerDomainService {

    public static final String UTC = "UTC";

    @Override
    public CustomerCreatedEvent validateAndInitiateCustomer(Customer customer) {
        customer.initializeCustomer();
        customer.validateCustomer();
        log.info("Customer with id: {} is initiated", customer.getId().value());
        return new CustomerCreatedEvent(customer, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void updateCustomer(Customer customer, String familyName, String givenName, Email email, PhoneNumber phoneNumber) {
        customer.updateDetails(familyName, givenName, email, phoneNumber);
        log.info("Customer with id: {} details updated", customer.getId().value());
    }

    @Override
    public void deactivateCustomer(Customer customer) {
        customer.deactivate();
        log.info("Customer with id: {} is deactivated", customer.getId().value());
    }

    @Override
    public void activateCustomer(Customer customer) {
        customer.activate();
        log.info("Customer with id: {} is activated", customer.getId().value());
    }
}
