package co.khmer.samrouth.ecommerce.payment.dto;

import co.khmer.samrouth.ecommerce.order.valueobject.TransactionType;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record CreditHistoryDto(
        UUID id,
        UUID customerId,
        BigDecimal amount,
        TransactionType transactionType
) {
}
