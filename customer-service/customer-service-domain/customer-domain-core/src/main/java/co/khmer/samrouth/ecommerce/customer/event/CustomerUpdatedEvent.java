package co.khmer.samrouth.ecommerce.customer.event;

import co.khmer.samrouth.ecommerce.customer.entity.Customer;
import co.khmer.samrouth.ecommerce.order.event.DomainEvent;

import java.time.ZonedDateTime;

public class CustomerUpdatedEvent extends CustomerEvent{
    private final ZonedDateTime updatedAt;

    public CustomerUpdatedEvent(Customer customer, ZonedDateTime updatedAt){
        super(customer);
        this.updatedAt = updatedAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }
}
