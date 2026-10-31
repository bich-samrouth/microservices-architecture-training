package co.khmer.samrouth.ecommerce.user.service.impl;

import co.khmer.samrouth.ecommerce.user.config.KeycloakAdminClientConfig;
import co.khmer.samrouth.ecommerce.user.exception.KeycloakException;
import co.khmer.samrouth.ecommerce.user.service.KeycloakUserService;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeycloakUserServiceImpl implements KeycloakUserService {
    private final Keycloak keycloak;

    @Override
    public void createKeycloakUser(UserRepresentation userRepresentation) {
        try (Response response = keycloak.realm(KeycloakAdminClientConfig.REALM_NAME)
                .users()
                .create(userRepresentation)) {

            if (response.getStatus() != HttpStatus.CREATED.value()) {
                throw new KeycloakException(
                        HttpStatus.valueOf(response.getStatus()),
                        "Create user in keycloak failed with status " + response.getStatus()
                );
            }
        }
    }

    @Override
    public UserRepresentation getKeycloakUserByUsername(String username) {
        return keycloak.realm(KeycloakAdminClientConfig.REALM_NAME)
                .users()
                .search(username, true)
                .stream()
                .findFirst()
                .orElseThrow(() -> new KeycloakException(
                        HttpStatus.NOT_FOUND,
                        "User in Keycloak not found"
                ));
    }

    @Override
    public void updateKeycloakUser(String userId ,UserRepresentation userRepresentation) {
        try {
            keycloak.realm(KeycloakAdminClientConfig.REALM_NAME)
                    .users()
                    .get(userId)
                    .update(userRepresentation);
        } catch (Exception e){
            throw new KeycloakException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Update user in keycloak failed"
            );
        }
    }

    @Override
    public UserRepresentation getKeycloakUserById(String userId) {
        return keycloak.realm(KeycloakAdminClientConfig.REALM_NAME)
                .users()
                .get(userId)
                .toRepresentation();
    }

    @Override
    public Slice<UserRepresentation> getKeycloakUsersByRoleName(String roleName, Pageable pageable) {
        List<UserRepresentation> userRepresentationList  = keycloak.realm(KeycloakAdminClientConfig.REALM_NAME)
                .roles()
                .get(roleName)
                .getUserMembers((int) pageable.getOffset(),pageable.getPageSize() +1);

        boolean hasNext = userRepresentationList .size() > pageable.getPageSize();
        List<UserRepresentation> content = hasNext ?
                userRepresentationList.subList(0,pageable.getPageSize()):userRepresentationList ;

        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public Slice<UserRepresentation> getKeycloakUsers(Pageable pageable) {
        List<UserRepresentation> userRepresentationList = keycloak.realm(KeycloakAdminClientConfig.REALM_NAME)
                .users()
                .list((int) pageable.getOffset(), pageable.getPageSize() + 1);

        boolean hasNext = userRepresentationList.size() > pageable.getPageSize();
        List<UserRepresentation> content = hasNext ? userRepresentationList.subList(0, pageable.getPageSize()) : userRepresentationList;
        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public void deleteKeycloakUserById(String userId) {
        try (Response response = keycloak.realm(KeycloakAdminClientConfig.REALM_NAME)
                .users()
                .delete(userId)) {
            if (response.getStatus() != HttpStatus.NO_CONTENT.value()) {
                throw new KeycloakException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Delete user in keycloak failed with status " + response.getStatus()
                );
            }
        }
    }
}
