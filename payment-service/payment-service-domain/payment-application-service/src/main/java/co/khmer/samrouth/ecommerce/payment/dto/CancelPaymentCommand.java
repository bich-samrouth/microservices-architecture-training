package co.khmer.samrouth.ecommerce.payment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CancelPaymentCommand(
        @NotNull UUID paymentId
) {
}
