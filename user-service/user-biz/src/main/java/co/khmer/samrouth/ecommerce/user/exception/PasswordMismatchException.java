package co.khmer.samrouth.ecommerce.user.exception;

import co.khmer.samrouth.restapi.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class PasswordMismatchException extends GlobalException {
    public PasswordMismatchException(HttpStatus status, String message) {
        super(status, message);
    }
}
