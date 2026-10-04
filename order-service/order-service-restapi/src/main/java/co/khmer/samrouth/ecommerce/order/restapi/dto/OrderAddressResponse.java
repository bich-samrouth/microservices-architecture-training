package co.khmer.samrouth.ecommerce.order.restapi.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record OrderAddressResponse(
        UUID id,
        String street,
        String postalCode,
        String city
) {
}
