package co.khmer.samrouth.ecommerce.order.port.output;

import co.khmer.samrouth.ecommerce.order.entity.Order;

public interface OrderRepository {

    // save order
    Order saveOrder(Order order);
}
