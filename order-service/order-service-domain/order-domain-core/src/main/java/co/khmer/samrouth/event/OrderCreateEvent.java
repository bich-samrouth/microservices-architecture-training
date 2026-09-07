package co.khmer.samrouth.event;


import co.khmer.samrouth.entity.Order;

import java.time.ZonedDateTime;

public class OrderCreateEvent extends OrderEvent {
    public OrderCreateEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
    }
}
