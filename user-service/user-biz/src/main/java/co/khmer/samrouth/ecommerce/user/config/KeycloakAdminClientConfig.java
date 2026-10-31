package co.khmer.samrouth.ecommerce.user.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakAdminClientConfig {
    public final static String REALM_NAME = "ecommerce";

    @Bean
    public Keycloak keycloak(){
        return KeycloakBuilder
                .builder()
                .serverUrl("http://localhost:8888")
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId("ecommerce-admin-client")
                .clientSecret("khyHXn1lIzbGn5mU18M5XvxTfrAOIknLTmk4bVBgH85njvUq8RZBvsEuyCA05qkyHZ1rXJm0KcLNmQOrOD0eUQ")
                .realm(REALM_NAME)
                .build();
    }
}
