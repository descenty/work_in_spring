package com.descenty.work_in_spring.user.service;

import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import com.descenty.work_in_spring.user.dto.Credential;
import com.descenty.work_in_spring.user.dto.LoginRequest;
import com.descenty.work_in_spring.user.dto.OAuth2SignUpRequest;
import com.descenty.work_in_spring.user.dto.SignUpRequest;
import com.descenty.work_in_spring.user.dto.TokenResponse;

import io.micrometer.core.ipc.http.HttpSender.Response;

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

    private TokenResponse clientAuthentication() {
        WebClient client = WebClient.create();

        return client.post()
                .uri(serverURL + "/realms/" + realm
                        + "/protocol/openid-connect/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue("grant_type=client_credentials&client_id="
                        + clientID + "&client_secret=" + clientSecret)
                .retrieve().bodyToMono(TokenResponse.class).block();

    }

    public TokenResponse login(LoginRequest request) {
        WebClient client = WebClient.create();

        return client.post()
                .uri(serverURL + "/realms/" + realm
                        + "/protocol/openid-connect/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue("grant_type=password&client_id=" + clientID
                        + "&client_secret=" + clientSecret + "&username="
                        + request.email() + "&password=" + request.password())
                .retrieve().bodyToMono(TokenResponse.class).block();
    }

    public String createUser(SignUpRequest request) {
        WebClient client = WebClient.create();
        try {
            URI location = client.post()
                    .uri(serverURL + "/admin/realms/" + realm + "/users")
                    .header("Authorization",
                            "Bearer " + clientAuthentication().access_token())
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(new OAuth2SignUpRequest(request.email(), true,
                            new Credential[] { new Credential("password",
                                    request.password()) }))
                    .retrieve().toBodilessEntity().block().getHeaders()
                    .getLocation();
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
}