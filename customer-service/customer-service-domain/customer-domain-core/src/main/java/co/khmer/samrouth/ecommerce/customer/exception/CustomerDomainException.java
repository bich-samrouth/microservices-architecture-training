package co.khmer.samrouth.ecommerce.customer.exception;

import co.khmer.samrouth.ecommerce.order.exception.DomainException;

public class CustomerDomainException  extends DomainException {
    public CustomerDomainException(String message) {
        super(message);
    }
}