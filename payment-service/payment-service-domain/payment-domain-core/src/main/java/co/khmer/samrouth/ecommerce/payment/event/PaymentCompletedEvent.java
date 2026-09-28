package co.khmer.samrouth.ecommerce.payment.event;

import co.khmer.samrouth.ecommerce.payment.entity.Payment;

import java.time.ZonedDateTime;
import java.util.List;

public class  PaymentCompletedEvent extends PaymentEvent {

    public PaymentCompletedEvent(Payment payment, ZonedDateTime createdAt, List<String> failureMessages) {
        super(payment, createdAt, failureMessages);
    }
}
