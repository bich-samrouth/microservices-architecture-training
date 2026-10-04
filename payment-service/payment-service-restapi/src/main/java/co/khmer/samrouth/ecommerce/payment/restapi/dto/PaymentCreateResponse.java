package co.khmer.samrouth.ecommerce.payment.restapi.dto;

import co.khmer.samrouth.ecommerce.order.valueobject.PaymentStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record PaymentCreateResponse(
        UUID paymentId,
        UUID orderId,
        UUID customerId,
        BigDecimal price,
        PaymentStatus paymentStatus,
        ZonedDateTime createdAt,
        List<String> failureMessages
) {
}
