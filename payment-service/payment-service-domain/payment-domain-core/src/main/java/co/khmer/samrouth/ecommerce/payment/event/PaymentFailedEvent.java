package co.khmer.samrouth.ecommerce.payment.event;

import co.khmer.samrouth.ecommerce.payment.entity.Payment;

import java.time.ZonedDateTime;
import java.util.List;

public abstract class PaymentFailedEvent extends PaymentEvent {

    protected PaymentFailedEvent(Payment payment, ZonedDateTime createdAt, List<String> failureMessages) {
        super(payment, createdAt, failureMessages);
    }
}
