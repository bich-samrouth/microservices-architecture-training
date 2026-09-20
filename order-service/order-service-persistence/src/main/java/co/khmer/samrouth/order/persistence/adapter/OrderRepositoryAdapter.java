package co.khmer.samrouth.order.persistence.adapter;

import co.khmer.samrouth.domain.entity.Order;
import co.khmer.samrouth.order.persistence.repository.OrderJpaRepository;
import co.khmer.samrouth.order.domain.port.output.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepository {
    private final OrderJpaRepository orderJapRepository;

    @Override
    public void saveOrder(Order order) {

    }
}
