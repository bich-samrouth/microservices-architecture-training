package co.khmer.samrouth.ecommerce.payment.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record CreditEntryDto(
        UUID id,
        UUID customerId,
        BigDecimal totalCreditAmount
) {
}
