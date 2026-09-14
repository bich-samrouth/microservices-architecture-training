package co.khmer.samrouth.domain.event;


import co.khmer.samrouth.domain.entity.Order;

import java.time.ZonedDateTime;

public class OrderCreateEvent extends OrderEvent {
    public OrderCreateEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
    }
}
