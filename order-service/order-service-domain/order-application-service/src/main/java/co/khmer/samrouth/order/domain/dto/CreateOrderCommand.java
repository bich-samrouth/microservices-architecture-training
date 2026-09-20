package co.khmer.samrouth.order.domain.dto;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateOrderCommand(
        UUID customerId,
        UUID businessId,
        BigDecimal price,
        CommandOrderAddress deliveryAddress,
        List<CommandOrderItem> items
) {
}
