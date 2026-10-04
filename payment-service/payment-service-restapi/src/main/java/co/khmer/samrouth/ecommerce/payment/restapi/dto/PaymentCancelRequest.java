package co.khmer.samrouth.ecommerce.payment.restapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record PaymentCancelRequest(
        @NotNull UUID paymentId
) {
}
