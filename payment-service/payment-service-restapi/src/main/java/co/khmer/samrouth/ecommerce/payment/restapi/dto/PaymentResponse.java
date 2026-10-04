package co.khmer.samrouth.ecommerce.payment.restapi.dto;

import co.khmer.samrouth.ecommerce.order.valueobject.PaymentStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Builder
public record PaymentResponse(
        UUID id,
        UUID orderId,
        UUID customerId,
        BigDecimal price,
        PaymentStatus paymentStatus,
        ZonedDateTime createdAt,
        ZonedDateTime updatedAt
) {
}
