package co.khmer.samrouth.ecommerce.customer.event;

import co.khmer.samrouth.ecommerce.customer.entity.Customer;
import co.khmer.samrouth.ecommerce.order.event.DomainEvent;

import java.time.ZonedDateTime;

public class CustomerCreatedEvent implements DomainEvent<Customer> {
    private final Customer customer;
    private final ZonedDateTime createdAt;

    public CustomerCreatedEvent(Customer customer, ZonedDateTime createdAt) {
        this.customer = customer;
        this.createdAt = createdAt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
