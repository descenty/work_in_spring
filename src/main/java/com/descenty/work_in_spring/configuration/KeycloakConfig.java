package com.descenty.work_in_spring.configuration;

import org.keycloak.OAuth2Constants;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@KeycloakConfiguration
public class KeycloakConfig {

    @Value("${KC_URL}")
    public String serverURL;
    @Value("${KC_REALM}")
    public String realm;
    @Value("${KC_CLIENT_ID}")
    public String clientID;
    @Value("${KC_CLIENT_SECRET}")
    public String clientSecret;

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder().realm(realm).serverUrl(serverURL)
                .clientId(clientID).clientSecret(clientSecret)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS).build();
    }

    @Bean
    public KeycloakConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }
}