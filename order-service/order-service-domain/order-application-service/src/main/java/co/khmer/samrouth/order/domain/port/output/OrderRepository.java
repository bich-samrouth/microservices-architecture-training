package co.khmer.samrouth.order.domain.port.output;

import co.khmer.samrouth.domain.entity.Order;

public interface OrderRepository {

    // save order
    Order saveOrder(Order order);
}
