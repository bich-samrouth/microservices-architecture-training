package co.khmer.samrouth.ecommerce.user.dto;

import lombok.Builder;

@Builder
public record UserResponse(
        String id,
        String familyName,
        String givenName,
        String gender,
        String birthday,
        String mobilePhone,
        String address
) {
}
