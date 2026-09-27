package co.khmer.samrouth.ecommerce.payment.excetion;

import co.khmer.samrouth.ecommerce.order.exception.DomainException;

public class PaymentDomainException extends DomainException {
    public PaymentDomainException(String message) {
        super(message);
    }

    public PaymentDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
