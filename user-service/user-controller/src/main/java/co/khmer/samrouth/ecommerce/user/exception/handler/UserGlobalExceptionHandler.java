package co.khmer.samrouth.ecommerce.user.exception.handler;

import co.khmer.samrouth.ecommerce.user.exception.*;
import co.khmer.samrouth.restapi.dto.RestApiErrorResponse;
import co.khmer.samrouth.restapi.exception.GlobalException;
import co.khmer.samrouth.restapi.exception.GlobalExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserGlobalExceptionHandler extends GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(KeycloakException.class)
    public RestApiErrorResponse<?> handle(
            KeycloakException e
    ) {
        return build(e);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PasswordMismatchException.class)
    public RestApiErrorResponse<?> handle(
            PasswordMismatchException e
    ) {
        return build(e);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UsernameConflictException.class)
    public RestApiErrorResponse<?> handle(
            UsernameConflictException e
    ) {
        return build(e);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public RestApiErrorResponse<?> handle(
            UserNotFoundException e
    ) {
        return build(e);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UserHybridDataInconsistencyException.class)
    public RestApiErrorResponse<?> handle(
            UserHybridDataInconsistencyException e
    ) {
        return build(e);
    }


    private RestApiErrorResponse<?> build(GlobalException e) {
        return RestApiErrorResponse.builder()
                .code(e.getStatus().getReasonPhrase())
                .message(e.getMessage())
                .build();
    }

}
