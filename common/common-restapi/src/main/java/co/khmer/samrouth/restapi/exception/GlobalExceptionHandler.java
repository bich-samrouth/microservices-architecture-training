package co.khmer.samrouth.restapi.exception;

import co.khmer.samrouth.restapi.dto.FieldErrorResponse;
import co.khmer.samrouth.restapi.dto.RestApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestApiErrorResponse<?> handleException(
            MethodArgumentNotValidException e
    ){
        return  RestApiErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Date validation failed")
                .details(extractFieldError(e.getFieldErrors()))
                .build();
    }

    private List<FieldErrorResponse> extractFieldError(List<FieldError> fieldErrors){
        return fieldErrors.stream()
                .map(fieldError -> new FieldErrorResponse(
                        fieldError.getField(),
                        fieldError.getCode(),
                        fieldError.getDefaultMessage()
                        )).toList();
    }
}
