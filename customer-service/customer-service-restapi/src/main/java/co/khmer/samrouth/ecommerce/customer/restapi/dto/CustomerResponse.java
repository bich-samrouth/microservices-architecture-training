package co.khmer.samrouth.ecommerce.customer.restapi.dto;

import co.khmer.samrouth.ecommerce.order.valueobject.CustomerStatus;
import co.khmer.samrouth.ecommerce.order.valueobject.LoyaltyTier;

import java.util.UUID;

public record CustomerResponse(
        UUID id,
        String username,
        String familyName,
        String givenName,
        String email,
        String phoneNumber,
        LoyaltyTier loyaltyTier,
        CustomerStatus status
) {
}
