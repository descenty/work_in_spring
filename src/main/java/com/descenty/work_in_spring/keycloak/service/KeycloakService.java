package com.descenty.work_in_spring.keycloak.service;

import lombok.RequiredArgsConstructor;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.descenty.work_in_spring.keycloak.dto.LoginRequest;
import com.descenty.work_in_spring.keycloak.dto.SignUpRequest;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.Arrays;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class KeycloakService {
    @Value("${KC_URL}")
    public String serverURL;
    @Value("${KC_REALM}")
    public String realm;
    @Value("${KC_CLIENT_ID}")
    public String clientID;
    @Value("${KC_CLIENT_SECRET}")
    public String clientSecret;

    private final Keycloak keycloakAdminClient;

    public void registerExisting(SignUpRequest request) {
        this.createKeycloakUser(request);
    }

    public AccessTokenResponse login(LoginRequest request) {
        try (Keycloak keycloak = this.keycloakCredentialBuilder(request)) {
            return keycloak.tokenManager().getAccessToken();
        } catch (NotAuthorizedException e) {
            throw new NotAuthorizedException(e.getMessage());
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public String createKeycloakUser(SignUpRequest request) {

        // TODO NEED TO SEND REQUEST YOURSELF
        // ****
        Keycloak keycloak = keycloakAdminBuilder();
        UsersResource usersResource = keycloak.realm(this.realm).users();
        CredentialRepresentation credentialRepresentation = createPasswordCredentials(
                request.password());

        String access_token = keycloak.tokenManager().getAccessTokenString()


        UserRepresentation user = new UserRepresentation();
        // user.setUsername(request.email());
        user.setEmail(request.email());
        user.setEnabled(true);
        user.setCredentials(
                Collections.singletonList(credentialRepresentation));
        // {{OAUTH2_URL}}/admin/realms/{{OAUTH2_REALM}}/users
        System.out.print(user.geta)
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s/admin/realms/%s/users",
                        serverURL, realm)))
                .header("Authorization", "Bearer " + access_token)
                .header("Content-Type", "application/json")
                // .POST(HttpRequest.BodyPublishers.ofString(user))
                .build();
        // try (Response response = usersResource.create(user)) {
            // System.out.println(response.getStatus());
            // return response.getStatusInfo().getReasonPhrase();
        // } catch (Exception e) {
            // System.out.println(e.getMessage());
        // }
        // return null;
    }

    private Keycloak keycloakAdminBuilder() {
        return KeycloakBuilder.builder().realm(realm).serverUrl(serverURL)
                .clientId(clientID).clientSecret(clientSecret)
                .username("admin@workin.ru").password("qweasdzxc")
                .grantType(OAuth2Constants.PASSWORD).build();
    }

    private Keycloak keycloakCredentialBuilder(LoginRequest request) {
        return KeycloakBuilder.builder().realm(this.realm)
                .serverUrl(this.serverURL).clientId(this.clientID)
                .clientSecret(this.clientSecret).username(request.email())
                .password(request.password()).build();
    }

    private CredentialRepresentation createPasswordCredentials(
            String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }
}