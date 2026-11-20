package co.khmer.samrouth.ecommerce.customer.restapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerCreateRequest(
        @NotBlank
        String userId,
        @NotBlank
        String username,
        String familyName,
        String givenName,
        @NotBlank
        @Email
        String email,
        String phoneNumber
) {
}
