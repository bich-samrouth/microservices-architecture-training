package co.khmer.samrouth.ecommerce.payment.persistence.entity;

import co.khmer.samrouth.ecommerce.order.valueobject.CustomerId;
import co.khmer.samrouth.ecommerce.order.valueobject.Money;
import co.khmer.samrouth.ecommerce.order.valueobject.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "credit_historys")
public class CreditHistoryEntity {
    @Id
    private UUID id;
    private UUID customerId;
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
}
