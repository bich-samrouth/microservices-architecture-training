package co.khmer.samrouth.ecommerce.order.restapi.dto;

import co.khmer.samrouth.ecommerce.order.valueobject.OrderStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
public record OrderResponse(
        UUID orderId,
        UUID customerId,
        UUID businessId,
        BigDecimal price,
        OrderStatus orderStatus,
        UUID trackingId,
        OrderAddressResponse deliveryAddress,
        List<OrderItemResponse> items,
        List<String> failureMessages
) {
}
