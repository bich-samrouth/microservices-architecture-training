package co.khmer.samrouth.ecommerce.payment.service;

import co.khmer.samrouth.ecommerce.order.entity.Order;
import co.khmer.samrouth.ecommerce.payment.entity.CreditEntry;
import co.khmer.samrouth.ecommerce.payment.entity.CreditHistory;
import co.khmer.samrouth.ecommerce.payment.entity.Payment;
import co.khmer.samrouth.ecommerce.payment.event.PaymentCancelledEvent;
import co.khmer.samrouth.ecommerce.payment.event.PaymentCompletedEvent;
import co.khmer.samrouth.ecommerce.payment.event.PaymentEvent;
import co.khmer.samrouth.ecommerce.payment.event.PaymentFailedEvent;

import java.util.List;

public interface PaymentDomainService {
    PaymentEvent validateAndInitiatePayment(Payment payment,
                                            CreditEntry creditEntry,
                                            List<CreditHistory> creditHistories,
                                            List<String> failureMessages);
    // Cancel payment
    PaymentEvent cancelPayment(Payment payment,
                               CreditEntry creditEntry,
                               List<CreditHistory> creditHistories,
                               List<String> failureMessages);

    PaymentEvent failPayment(Payment payment, List<String> failureMessages);
}
