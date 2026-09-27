package co.khmer.samrouth.ecommerce.order.service;

import co.khmer.samrouth.ecommerce.order.entity.Business;
import co.khmer.samrouth.ecommerce.order.entity.Order;
import co.khmer.samrouth.ecommerce.order.event.OrderCancelledEvent;
import co.khmer.samrouth.ecommerce.order.event.OrderCreatedEvent;
import co.khmer.samrouth.ecommerce.order.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {
    OrderCreatedEvent validateAndInitiateOrder(Order order, Business business);
    OrderPaidEvent payOrder(Order order);
    void approveOrder(Order order);
    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);
    void cancelOrder(Order order ,List<String>failureMessages );
}
