package co.khmer.samrouth.ecommerce.user.service.impl;

import co.khmer.samrouth.ecommerce.user.config.KeycloakAdminClientConfig;
import co.khmer.samrouth.ecommerce.user.service.KeycloakRoleService;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakRoleServiceImpl implements KeycloakRoleService {
    private final Keycloak keycloak;

    @Override
    public void assignRoleToUser(String userId, String roleNameWithDelimiter) {
        String[] roleNames = roleNameWithDelimiter.split(",");
        List<RoleRepresentation> roleRepresentationList  = Arrays.stream(roleNames)
                .map(this::getKeycloakRoleByName)
                .toList();

        keycloak.realm(KeycloakAdminClientConfig.REALM_NAME)
                .users().
                get(userId)
                .roles()
                .realmLevel()
                .add(roleRepresentationList);

    }

    private RoleRepresentation getKeycloakRoleByName(String roleName){
        return keycloak.realm(KeycloakAdminClientConfig.REALM_NAME)
                .roles()
                .get(roleName)
                .toRepresentation();
    }
}
