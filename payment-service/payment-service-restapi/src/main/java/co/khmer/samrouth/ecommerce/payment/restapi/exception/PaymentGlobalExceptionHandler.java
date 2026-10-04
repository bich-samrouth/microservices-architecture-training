package co.khmer.samrouth.ecommerce.payment.restapi.exception;

import co.khmer.samrouth.ecommerce.payment.exception.CreditEntryNotFoundException;
import co.khmer.samrouth.ecommerce.payment.exception.PaymentApplicationServiceException;
import co.khmer.samrouth.ecommerce.payment.exception.PaymentDomainException;
import co.khmer.samrouth.ecommerce.payment.exception.PaymentNotFoundException;
import co.khmer.samrouth.restapi.dto.RestApiErrorResponse;
import co.khmer.samrouth.restapi.exception.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class PaymentGlobalExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(PaymentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RestApiErrorResponse<?> handlePaymentNotFoundException(PaymentNotFoundException ex) {
        return RestApiErrorResponse.builder()
                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(CreditEntryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RestApiErrorResponse<?> handleCreditEntryNotFoundException(CreditEntryNotFoundException ex) {
        return RestApiErrorResponse.builder()
                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(PaymentDomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestApiErrorResponse<?> handlePaymentDomainException(PaymentDomainException ex) {
        return RestApiErrorResponse.builder()
                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(PaymentApplicationServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestApiErrorResponse<?> handlePaymentApplicationServiceException(PaymentApplicationServiceException ex) {
        return RestApiErrorResponse.builder()
                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .build();
    }
}
