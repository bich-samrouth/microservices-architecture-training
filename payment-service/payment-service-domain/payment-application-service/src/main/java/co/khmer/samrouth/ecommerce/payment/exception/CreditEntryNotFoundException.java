package co.khmer.samrouth.ecommerce.payment.exception;

public class CreditEntryNotFoundException extends RuntimeException {
    public CreditEntryNotFoundException(String message) {
        super(message);
    }
}
