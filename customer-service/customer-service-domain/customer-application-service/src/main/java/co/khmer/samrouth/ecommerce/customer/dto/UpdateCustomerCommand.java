package co.khmer.samrouth.ecommerce.customer.dto;

import java.util.UUID;

public record UpdateCustomerCommand(
        UUID customerId,
        String familyName,
        String givenName,
        String email,
        String phoneNumber
) {
}
