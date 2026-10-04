package co.khmer.samrouth.ecommerce.customer.restapi.exception;

import co.khmer.samrouth.ecommerce.customer.exception.CustomerAlreadyExistsException;
import co.khmer.samrouth.ecommerce.customer.exception.CustomerDomainException;
import co.khmer.samrouth.ecommerce.customer.exception.CustomerNotFoundException;
import co.khmer.samrouth.restapi.dto.RestApiErrorResponse;
import co.khmer.samrouth.restapi.exception.GlobalExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomerGlobalExceptionHandler extends GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomerNotFoundException.class)
    public RestApiErrorResponse<?> handleCustomerNotFoundException(CustomerNotFoundException e) {
        return RestApiErrorResponse.builder()
                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public RestApiErrorResponse<?> handleCustomerAlreadyExistsException(CustomerAlreadyExistsException e) {
        return RestApiErrorResponse.builder()
                .code(HttpStatus.CONFLICT.getReasonPhrase())
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomerDomainException.class)
    public RestApiErrorResponse<?> handleCustomerDomainException(CustomerDomainException e) {
        return RestApiErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(e.getMessage())
                .build();
    }
}
