package co.khmer.samrouth.ecommerce.order.event;


import co.khmer.samrouth.ecommerce.order.entity.Order;

import java.time.ZonedDateTime;

public class OrderCreatedEvent extends OrderEvent {
    public OrderCreatedEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
    }
}
