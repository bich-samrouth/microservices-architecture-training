package co.khmer.samrouth.ecommerce.user.service.impl;

import co.khmer.samrouth.ecommerce.user.dto.UserCreateRequest;
import co.khmer.samrouth.ecommerce.user.dto.UserResponse;
import co.khmer.samrouth.ecommerce.user.dto.UserUpdateRequest;
import co.khmer.samrouth.ecommerce.user.entity.UserEntity;
import co.khmer.samrouth.ecommerce.user.exception.PasswordMismatchException;
import co.khmer.samrouth.ecommerce.user.exception.UserHybridDataInconsistencyException;
import co.khmer.samrouth.ecommerce.user.exception.UserNotFoundException;
import co.khmer.samrouth.ecommerce.user.exception.UsernameConflictException;
import co.khmer.samrouth.ecommerce.user.mapper.UserMapper;
import co.khmer.samrouth.ecommerce.user.repository.UserRepository;
import co.khmer.samrouth.ecommerce.user.service.KeycloakRoleService;
import co.khmer.samrouth.ecommerce.user.service.KeycloakUserService;
import co.khmer.samrouth.ecommerce.user.service.UserService;
import co.khmer.samrouth.restapi.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final KeycloakUserService keycloakUserService;
    private final KeycloakRoleService keycloakRoleService;
    private final JsonMapper.Builder builder;

    @Override
    public UserResponse create(UserCreateRequest userCreateRequest) {
        // Map user dto to user entity
        UserEntity userEntity = userMapper.toEntity(userCreateRequest);

        // Validate username
        if (userRepository.existsByUsername(userCreateRequest.username())) {
            throw new UsernameConflictException(HttpStatus.CONFLICT, "Username already exists");
        }

        // Validate password
        if (!userCreateRequest.password().equals(userCreateRequest.confirmedPassword())) {
            throw new PasswordMismatchException(HttpStatus.BAD_REQUEST, "Password does not match");
        }

        // Save user to keycloak
        // After save user to keycloak , save user in database
        UserRepresentation userRepresentation = userMapper.toRepresentation(userCreateRequest);
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(false);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(userCreateRequest.password());
        userRepresentation.setCredentials(List.of(credentialRepresentation));

        keycloakUserService.createKeycloakUser(userRepresentation);
        UserRepresentation createdUser = keycloakUserService.getKeycloakUserByUsername(
                userEntity.getUsername()
        );
        keycloakRoleService.assignRoleToUser(createdUser.getId() ,userCreateRequest.roleName());

        userEntity.setId(createdUser.getId());
        userRepository.save(userEntity);

        return userMapper.toResponse(userEntity,createdUser);
    }

    @Override
    public UserResponse update(String userId, UserUpdateRequest userUpdateRequest) {
        UserEntity userEntity = userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));

        userMapper.toEntity(userUpdateRequest,userEntity);
        userRepository.save(userEntity);

        UserRepresentation userRepresentation =
                keycloakUserService.getKeycloakUserByUsername(userEntity.getUsername());

        // attempt to keycloak data
        if(!(userUpdateRequest.familyName() == null && userUpdateRequest.givenName() == null))
        {
            if(userUpdateRequest.familyName() != null)
                userRepresentation.setFirstName(userUpdateRequest.familyName());
            if(userUpdateRequest.givenName() != null)
                userRepresentation.setLastName(userUpdateRequest.givenName());
            keycloakUserService.updateKeycloakUser(userId,userRepresentation);
        }

        return userMapper.toResponse(userEntity,userRepresentation);
    }

    @Override
    public UserResponse getById(String userId) {
        UserEntity userEntity = userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));
        UserRepresentation userRepresentation =
                keycloakUserService.getKeycloakUserByUsername(userEntity.getUsername());
        return userMapper.toResponse(
                userEntity,
                userRepresentation
        );
    }

    @Override
    public void deleteById(String userId) {
        UserEntity userEntity = userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));
        userRepository.delete(userEntity);
        keycloakUserService.deleteKeycloakUserById(userId);
    }

    @Override
    public UserResponse getByUsername(String username) {
        UserEntity userEntity = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));
        UserRepresentation userRepresentation =
                keycloakUserService.getKeycloakUserByUsername(username);

        return userMapper.toResponse(
                userEntity,
                userRepresentation
        );
    }

    @Override
    public PageResponse<UserResponse> getUsersByRoleName(String roleName, Pageable pageable) {
        Slice<UserRepresentation> userRepresentationSlice = keycloakUserService.getKeycloakUsersByRoleName(roleName, pageable);
        return mergeUserEntityWithUserRepresentation(userRepresentationSlice);
    }

    @Override
    public PageResponse<UserResponse> getUsers(PageRequest pageable) {
        Slice<UserRepresentation> userRepresentationSlice = keycloakUserService.getKeycloakUsers(pageable);
        return mergeUserEntityWithUserRepresentation(userRepresentationSlice);
    }

    private PageResponse<UserResponse> mergeUserEntityWithUserRepresentation(Slice<UserRepresentation> userRepresentationSlice) {
        List<UserResponse> userResponseList = userRepresentationSlice
                .map(userRepresentation -> {
                    UserEntity userEntity = userRepository.findById(userRepresentation.getId())
                            .orElseThrow(() -> new UserHybridDataInconsistencyException(HttpStatus.INTERNAL_SERVER_ERROR,
                                    "User hybrid data is inconsistent"));
                    return userMapper.toResponse(userEntity, userRepresentation);
                })
                .toList();

        return PageResponse.<UserResponse> builder()
                .items(userResponseList)
                .page(userRepresentationSlice.getNumber())
                .size(userRepresentationSlice.getSize())
                .hasNext(userRepresentationSlice.hasNext())
                .build();
    }
}
