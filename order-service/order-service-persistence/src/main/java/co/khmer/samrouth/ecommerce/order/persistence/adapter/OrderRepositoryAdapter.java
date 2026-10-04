package co.khmer.samrouth.ecommerce.order.persistence.adapter;

import co.khmer.samrouth.ecommerce.order.entity.Order;
import co.khmer.samrouth.ecommerce.order.persistence.entity.OrderEntity;
import co.khmer.samrouth.ecommerce.order.persistence.mapper.OrderPersistenceMapper;
import co.khmer.samrouth.ecommerce.order.persistence.repository.OrderJpaRepository;
import co.khmer.samrouth.ecommerce.order.port.output.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepository {
    private final OrderJpaRepository orderJpaRepository;
    private final OrderPersistenceMapper orderPersistenceMapper;

    @Override
    public Order saveOrder(Order order) {
        // Map Order to OrderEntity
        OrderEntity orderEntity = orderPersistenceMapper.orderToOrderEntity(order);

        // Set order address
        if (orderEntity.getOrderAddress() != null) {
            orderEntity.getOrderAddress().setOrder(orderEntity);
        }

        // Set order items
        if (orderEntity.getItems() != null) {
            orderEntity.getItems().forEach(orderItemEntity -> orderItemEntity.setOrder(orderEntity));
        }

        // Save into database
        OrderEntity saveOrderEntity = orderJpaRepository.save(orderEntity);

        // Map OrderEntity to Order
        return orderPersistenceMapper.orderEntityToOrder(saveOrderEntity);
    }

    @Override
    public Optional<Order> findById(UUID orderId) {
        return orderJpaRepository.findById(orderId)
                .map(orderPersistenceMapper::orderEntityToOrder);
    }

    @Override
    public Optional<Order> findByTrackingId(UUID trackingId) {
        return orderJpaRepository.findByTrackingId(trackingId)
                .map(orderPersistenceMapper::orderEntityToOrder);
    }

    @Override
    public List<Order> findAll() {
        return orderPersistenceMapper.orderEntityListToOrderList(orderJpaRepository.findAll());
    }

    @Override
    public List<Order> findByCustomerId(UUID customerId) {
        return orderPersistenceMapper.orderEntityListToOrderList(orderJpaRepository.findByCustomerId(customerId));
    }

    @Override
    public void deleteById(UUID orderId) {
        orderJpaRepository.deleteById(orderId);
    }
}
