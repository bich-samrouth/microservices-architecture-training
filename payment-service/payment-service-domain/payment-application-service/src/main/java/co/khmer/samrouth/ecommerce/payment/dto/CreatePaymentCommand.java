package co.khmer.samrouth.ecommerce.payment.dto;

import co.khmer.samrouth.ecommerce.order.valueobject.PaymentStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record CreatePaymentCommand (
        UUID  orderId,
        UUID  customerId,
        BigDecimal price,
        PaymentStatus paymentStatus
){
}
