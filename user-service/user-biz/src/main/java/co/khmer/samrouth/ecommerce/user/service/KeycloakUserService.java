package co.khmer.samrouth.ecommerce.user.service;


import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface KeycloakUserService {
    void createKeycloakUser(UserRepresentation userRepresentation);
    UserRepresentation getKeycloakUserByUsername(String username);
    void updateKeycloakUser(String userId ,UserRepresentation userRepresentation);
    UserRepresentation getKeycloakUserById(String userId);
    Slice<UserRepresentation> getKeycloakUsersByRoleName(String roleName , Pageable pageable);
    Slice<UserRepresentation> getKeycloakUsers(Pageable pageable);
    void deleteKeycloakUserById(String userId);
}
