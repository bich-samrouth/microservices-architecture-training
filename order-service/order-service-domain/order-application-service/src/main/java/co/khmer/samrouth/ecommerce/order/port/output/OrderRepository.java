package co.khmer.samrouth.ecommerce.order.port.output;

import co.khmer.samrouth.ecommerce.order.entity.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    // save order (create / update)
    Order saveOrder(Order order);

    // find order by id
    Optional<Order> findById(UUID orderId);

    // find order by tracking id
    Optional<Order> findByTrackingId(UUID trackingId);

    // find all orders
    List<Order> findAll();

    // find orders by customer id
    List<Order> findByCustomerId(UUID customerId);

    // delete order by id
    void deleteById(UUID orderId);
}
