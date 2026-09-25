package co.khmer.samrouth.domain.service;

import co.khmer.samrouth.domain.entity.Business;
import co.khmer.samrouth.domain.entity.Order;
import co.khmer.samrouth.domain.event.OrderCancelledEvent;
import co.khmer.samrouth.domain.event.OrderCreatedEvent;
import co.khmer.samrouth.domain.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {
    OrderCreatedEvent validateAndInitiateOrder(Order order, Business business);
    OrderPaidEvent payOrder(Order order);
    void approveOrder(Order order);
    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);
    void cancelOrder(Order order ,List<String>failureMessages );
}
