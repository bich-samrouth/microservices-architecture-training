package co.khmer.samrouth.ecommerce.customer.restapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerUpdateRequest(
        @NotBlank(message = "Family name must not be blank")
        String familyName,

        @NotBlank(message = "Given name must not be blank")
        String givenName,

        @NotBlank(message = "Email must not be blank")
        @Email(message = "Email must be a valid email address")
        String email,

        String phoneNumber
) {
}
