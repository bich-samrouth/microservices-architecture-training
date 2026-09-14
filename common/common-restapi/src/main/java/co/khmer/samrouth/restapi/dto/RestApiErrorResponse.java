package co.khmer.samrouth.restapi.dto;

import lombok.Builder;

@Builder
public record RestApiErrorResponse<T>(
        String code,
        String message,
        T details
) {
}
