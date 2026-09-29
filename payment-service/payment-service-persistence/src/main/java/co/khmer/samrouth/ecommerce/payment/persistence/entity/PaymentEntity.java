package co.khmer.samrouth.ecommerce.payment.persistence.entity;

import co.khmer.samrouth.ecommerce.order.valueobject.OrderStatus;
import co.khmer.samrouth.ecommerce.order.valueobject.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class PaymentEntity {
    @Id
    private UUID id;

    private UUID orderId;
    private UUID customerId;
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
