package co.khmer.samrouth.ecommerce.order.persistence.adapter;

import co.khmer.samrouth.ecommerce.order.entity.Order;
import co.khmer.samrouth.ecommerce.order.persistence.entity.OrderEntity;
import co.khmer.samrouth.ecommerce.order.persistence.mapper.OrderPersistenceMapper;
import co.khmer.samrouth.ecommerce.order.persistence.repository.OrderJpaRepository;
import co.khmer.samrouth.ecommerce.order.port.output.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepository {
    private final OrderJpaRepository orderJapRepository;
    private final OrderPersistenceMapper orderPersistenceMapper;

    @Override
    public Order saveOrder(Order order) {
        // Map Order to OrderEntity
        OrderEntity orderEntity = orderPersistenceMapper.orderToOrderEntity(order);

        // Set order address
        orderEntity.getOrderAddress().setOrder(orderEntity);

        // Set order items
        orderEntity.getItems().forEach(orderItemEntity -> orderItemEntity.setOrder(orderEntity));

        // Save into database
        OrderEntity saveOrderEntity = orderJapRepository.save(orderEntity);

        // Map OrderEntity to Order
        return orderPersistenceMapper.orderEntityToOrder(saveOrderEntity);
    }
}
