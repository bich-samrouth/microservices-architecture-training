package co.khmer.samrouth.ecommerce.order.restapi.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record OrderItemResponse(
        Integer id,
        UUID productId,
        Integer quantity,
        BigDecimal price,
        BigDecimal subTotal
) {
}
