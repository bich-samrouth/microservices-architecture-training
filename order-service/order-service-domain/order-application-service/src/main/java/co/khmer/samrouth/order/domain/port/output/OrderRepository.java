package co.khmer.samrouth.order.domain.port.output;

import co.khmer.samrouth.domain.entity.Order;

public interface OrderRepository {

    // save order
    void saveOrder(Order order);
}
