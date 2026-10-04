package co.khmer.samrouth.ecommerce.customer.exception;

public class CustomerNotFoundException extends CustomerDomainException {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
