package co.khmer.samrouth.ecommerce.customer.event;

import co.khmer.samrouth.ecommerce.customer.entity.Customer;

import java.time.ZonedDateTime;

public class CustomerCreatedEvent extends CustomerEvent {
    private final ZonedDateTime createdAt;

    public CustomerCreatedEvent(Customer customer, ZonedDateTime createdAt){
        super(customer);
        this.createdAt = createdAt;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
