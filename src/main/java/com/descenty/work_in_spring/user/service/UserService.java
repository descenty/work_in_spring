package com.descenty.work_in_spring.user.service;

import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.descenty.work_in_spring.user.dto.Credential;
import com.descenty.work_in_spring.configuration.KeycloakJwtAuthenticationConverter;
import com.descenty.work_in_spring.resume.service.ResumeService;
import com.descenty.work_in_spring.user.dto.AuthRequest;
import com.descenty.work_in_spring.user.dto.OAuth2SignUpRequest;
import com.descenty.work_in_spring.user.dto.Role;
import com.descenty.work_in_spring.user.dto.TokenResponse;

import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Value("${KC_URL}")
    public String serverURL;
    @Value("${KC_REALM}")
    public String realm;
    @Value("${KC_CLIENT_ID}")
    public String clientID;
    @Value("${KC_CLIENT_SECRET}")
    public String clientSecret;

    private final ResumeService resumeService;

    public final KeycloakJwtAuthenticationConverter keycloakJwtAuthenticationConverter;

    private TokenResponse clientAuthentication() {
        WebClient client = WebClient.create();
        return client.post().uri(serverURL + "/realms/" + realm + "/protocol/openid-connect/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue("grant_type=client_credentials&client_id=" + clientID + "&client_secret=" + clientSecret)
                .retrieve().bodyToMono(TokenResponse.class).block();

    }

    public Optional<TokenResponse> authenticate(AuthRequest authRequest) {
        WebClient client = WebClient.create();
        try {
            String bodyValue = authRequest.getRefreshToken() == null
                    ? "grant_type=password&username=" + authRequest.getUsername() + "&password=" + authRequest.getPassword()
                            + "&client_id=" + clientID + "&client_secret=" + clientSecret
                    : "grant_type=refresh_token&refresh_token=" + authRequest.getRefreshToken() + "&client_id=" + clientID
                            + "&client_secret=" + clientSecret;
            return Optional.of(client.post().uri(serverURL + "/realms/" + realm + "/protocol/openid-connect/token")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED).bodyValue(bodyValue).retrieve()
                    .bodyToMono(TokenResponse.class).block());
        } catch (WebClientResponseException e) {
            return Optional.empty();
        }
    }

    public String createUser(AuthRequest authRequest) {
        WebClient client = WebClient.create();
        try {
            URI location = client.post().uri(serverURL + "/admin/realms/" + realm + "/users")
                    .header("Authorization", "Bearer " + clientAuthentication().access_token())
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(new OAuth2SignUpRequest(authRequest.getUsername(), true,
                            new Credential[] { new Credential("password", authRequest.getPassword()) }))
                    .retrieve().toBodilessEntity().block().getHeaders().getLocation();
            if (location != null)
                return location.toString().split("/")[7];
        } catch (WebClientResponseException e) {
            if (e.getStatusCode().value() == 409)
                return "User already exists";
            if (e.getStatusCode().value() == 400)
                return "Invalid request";
        }
        return "Something went wrong";
    }

    public String addRoles(UUID userID, Role[] roles) {
        WebClient client = WebClient.create();
        try {
            client.post().uri(serverURL + "/admin/realms/" + realm + "/users/" + userID + "/role-mappings/realm")
                    .header("Authorization", "Bearer " + clientAuthentication().access_token())
                    .contentType(MediaType.APPLICATION_JSON).bodyValue(roles).retrieve().toBodilessEntity().block();
            return "Roles added";
        } catch (WebClientResponseException e) {
            if (e.getStatusCode().value() == 404)
                return "User not found";
            if (e.getStatusCode().value() == 400)
                return "Invalid request";
        }
        return "Something went wrong";
    }

    public String removeRoles(UUID userID, Role[] roles) {
        WebClient client = WebClient.create();
        try {
            client.method(HttpMethod.DELETE)
                    .uri(serverURL + "/admin/realms/" + realm + "/users/" + userID + "/role-mappings/realm")
                    .header("Authorization", "Bearer " + clientAuthentication().access_token())
                    .contentType(MediaType.APPLICATION_JSON).bodyValue(roles).retrieve().toBodilessEntity().block();
            return "Roles removed";
        } catch (WebClientResponseException e) {
            if (e.getStatusCode().value() == 404)
                return "User not found";
            if (e.getStatusCode().value() == 400)
                return "Invalid request";
        }
        return "Something went wrong";
    }

    @Transactional
    public boolean delete(UUID id) {
        WebClient client = WebClient.create();
        try {
            client.delete().uri(serverURL + "/admin/realms/" + realm + "/users/" + id)
                    .header("Authorization", "Bearer " + clientAuthentication().access_token()).retrieve()
                    .toBodilessEntity().block();
            resumeService.deleteByUserId(id);
            return true;
        } catch (WebClientResponseException e) {
            return false;
        }
    }
}