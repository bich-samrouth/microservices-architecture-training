package co.khmer.samrouth.ecommerce.payment.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record CreatePaymentCommand(
        @NotNull UUID orderId,
        @NotNull UUID customerId,
        @NotNull @DecimalMin("0.01") BigDecimal price
) {
}
