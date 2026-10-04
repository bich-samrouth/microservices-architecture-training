package co.khmer.samrouth.ecommerce.order.usecase;

import co.khmer.samrouth.ecommerce.order.entity.Order;
import co.khmer.samrouth.ecommerce.order.excetion.OrderNotFoundException;
import co.khmer.samrouth.ecommerce.order.port.output.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class FindOrderByIdUseCase {

    private final OrderRepository orderRepository;

    public Order execute(UUID orderId) {
        log.info("Find order by id: {}", orderId);
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Could not find order with ID: " + orderId));
    }
}
