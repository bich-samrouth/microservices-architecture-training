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
public class FindOrderByTrackingIdUseCase {

    private final OrderRepository orderRepository;

    public Order execute(UUID trackingId) {
        log.info("Find order by tracking id: {}", trackingId);
        return orderRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new OrderNotFoundException("Could not find order with tracking ID: " + trackingId));
    }
}
