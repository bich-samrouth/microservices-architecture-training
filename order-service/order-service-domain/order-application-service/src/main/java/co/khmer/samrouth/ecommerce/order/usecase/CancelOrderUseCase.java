package co.khmer.samrouth.ecommerce.order.usecase;

import co.khmer.samrouth.ecommerce.order.entity.Order;
import co.khmer.samrouth.ecommerce.order.excetion.OrderNotFoundException;
import co.khmer.samrouth.ecommerce.order.port.output.OrderRepository;
import co.khmer.samrouth.ecommerce.order.service.OrderDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class CancelOrderUseCase {

    private final OrderDomainService orderDomainService;
    private final OrderRepository orderRepository;

    public void execute(UUID orderId, List<String> failureMessages) {
        log.info("Executing cancel order use case for orderId: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Could not find order with ID: " + orderId));

        orderDomainService.cancelOrder(order, failureMessages);
        orderRepository.saveOrder(order);
        log.info("Order with ID: {} has been cancelled", orderId);
    }
}
