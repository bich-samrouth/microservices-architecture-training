package co.khmer.samrouth.ecommerce.customer.dto;

import java.util.UUID;

public record CreateCustomerCommand(
        String userId,
        UUID customerId,
        String username,
        String familyName,
        String givenName,
        String email,
        String phoneNumber
) {
}