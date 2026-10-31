package co.khmer.samrouth.ecommerce.user.exception;

import co.khmer.samrouth.restapi.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends GlobalException {
    public UserNotFoundException(HttpStatus status, String message) {
        super(status, message);
    }
}
