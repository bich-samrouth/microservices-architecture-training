package co.khmer.samrouth.ecommerce.user.service;

public interface KeycloakRoleService {
    void assignRoleToUser(String userId, String roleNameWithDelimiter) ;
}
