package co.khmer.samrouth.restapi.dto;

public record FieldErrorResponse(
        String field,
        String code,
        String message
) {
}
