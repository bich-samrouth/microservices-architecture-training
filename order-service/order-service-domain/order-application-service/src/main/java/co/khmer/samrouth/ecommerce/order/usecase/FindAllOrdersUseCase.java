package co.khmer.samrouth.ecommerce.order.usecase;

import co.khmer.samrouth.ecommerce.order.entity.Order;
import co.khmer.samrouth.ecommerce.order.port.output.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class FindAllOrdersUseCase {

    private final OrderRepository orderRepository;

    public List<Order> execute(UUID customerId) {
        if (customerId != null) {
            log.info("Find orders for customer id: {}", customerId);
            return orderRepository.findByCustomerId(customerId);
        }
        log.info("Find all orders");
        return orderRepository.findAll();
    }
}
