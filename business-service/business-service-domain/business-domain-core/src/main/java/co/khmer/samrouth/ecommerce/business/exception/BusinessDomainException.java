package co.khmer.samrouth.ecommerce.business.exception;

import co.khmer.samrouth.ecommerce.order.exception.DomainException;

public class BusinessDomainException extends DomainException {

    public BusinessDomainException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessDomainException(String message) {
        super(message);
    }
}
