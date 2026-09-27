package co.khmer.samrouth.ecommerce.order.excetion;

import co.khmer.samrouth.ecommerce.order.exception.DomainException;

public class OrderDomainException extends DomainException {
    public OrderDomainException(String message) {
        super(message);
    }

    public OrderDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
