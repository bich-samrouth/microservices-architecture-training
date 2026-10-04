package co.khmer.samrouth.ecommerce.payment.restapi.dto;

import co.khmer.samrouth.ecommerce.order.valueobject.PaymentStatus;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record PaymentCancelResponse(
        UUID paymentId,
        PaymentStatus paymentStatus,
        List<String> failureMessages
) {
}
