package co.khmer.samrouth.ecommerce.user.service;


import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface KeycloakUserService {
    void createKeycloakUser(UserRepresentation userRepresentation);
    UserRepresentation getKeycloakUserByUsername(String username);
    void updateKeycloakUser(String userId ,UserRepresentation userRepresentation);
    Slice<UserRepresentation> getKeycloakUsersByRoleName(String roleName , Pageable pageable);
    Slice<UserRepresentation> getKeycloakUsers(Pageable pageable);
    void deleteKeycloakUserById(String userId);
}
