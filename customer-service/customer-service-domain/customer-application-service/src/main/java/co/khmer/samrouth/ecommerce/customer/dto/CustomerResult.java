package co.khmer.samrouth.ecommerce.customer.dto;

import java.util.UUID;

public record CustomerResult(
        UUID id,
        String familyName,
        String givenName,
        String email,
        String phoneNumber
) {
}
