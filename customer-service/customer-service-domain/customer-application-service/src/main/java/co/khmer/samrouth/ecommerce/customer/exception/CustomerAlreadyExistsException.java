package co.khmer.samrouth.ecommerce.customer.exception;

public class CustomerAlreadyExistsException extends CustomerDomainException {

    public CustomerAlreadyExistsException(String message) {
        super(message);
    }
}
