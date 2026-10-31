package co.khmer.samrouth.ecommerce.user.mapper;

import co.khmer.samrouth.ecommerce.user.dto.UserCreateRequest;
import co.khmer.samrouth.ecommerce.user.dto.UserResponse;
import co.khmer.samrouth.ecommerce.user.dto.UserUpdateRequest;
import co.khmer.samrouth.ecommerce.user.entity.UserEntity;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    default UserResponse toResponse(UserEntity userEntity ,UserRepresentation userRepresentation) {
        return UserResponse.builder()
                .id(userEntity.getId())
                .familyName(userRepresentation.getFirstName())
                .givenName(userRepresentation.getLastName())
                .address(userEntity.getAddress())
                .birthday(userEntity.getBirthday())
                .gender(userEntity.getGender())
                .mobilePhone(userEntity.getMobilePhone())
                .build();
    };

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toEntity(UserUpdateRequest userUpdateRequest, @MappingTarget UserEntity entity);

    UserEntity toEntity(UserCreateRequest userCreateRequest);

    @Mapping(source = "familyName", target = "firstName")
    @Mapping(source = "givenName", target = "lastName")
    UserRepresentation toRepresentation(UserCreateRequest userCreateRequest);
}
