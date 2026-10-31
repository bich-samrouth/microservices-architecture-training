package co.khmer.samrouth.ecommerce.user.exception;

import co.khmer.samrouth.restapi.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class UserHybridDataInconsistencyException extends GlobalException {
    public UserHybridDataInconsistencyException(HttpStatus status, String message) {
        super(status, message);
    }
}
