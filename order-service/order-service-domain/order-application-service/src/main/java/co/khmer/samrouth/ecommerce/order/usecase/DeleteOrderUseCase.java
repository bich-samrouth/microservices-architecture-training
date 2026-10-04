package co.khmer.samrouth.ecommerce.order.usecase;

import co.khmer.samrouth.ecommerce.order.excetion.OrderNotFoundException;
import co.khmer.samrouth.ecommerce.order.port.output.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeleteOrderUseCase {

    private final OrderRepository orderRepository;

    public void execute(UUID orderId) {
        log.info("Deleting order with ID: {}", orderId);
        orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Could not find order with ID: " + orderId));

        orderRepository.deleteById(orderId);
        log.info("Order with ID: {} deleted successfully", orderId);
    }
}
