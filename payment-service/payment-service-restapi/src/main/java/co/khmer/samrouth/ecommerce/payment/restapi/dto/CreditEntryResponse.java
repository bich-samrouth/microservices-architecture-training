package co.khmer.samrouth.ecommerce.payment.restapi.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record CreditEntryResponse(
        UUID id,
        UUID customerId,
        BigDecimal totalCreditAmount
) {
}
