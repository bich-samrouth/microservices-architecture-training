package co.khmer.samrouth.ecommerce.user.dto;

import lombok.Builder;

import java.util.Date;

@Builder
public record UserUpdateRequest (
        String familyName,
        String givenName,
        String gender,
        String address,
        Date birthday,
        String mobilePhone
){
}
