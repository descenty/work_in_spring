package com.descenty.work_in_spring.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@ConditionalOnProperty(name = "keycloak.enabled", havingValue = "true", matchIfMissing = true)
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfiguration {

        @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http.cors().configurationSource(request -> {
                        var cors = new org.springframework.web.cors.CorsConfiguration();
                        cors.setAllowedOrigins(java.util.List.of("http://localhost:19006"));
                        // cors.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE"));
                        // cors.setAllowedHeaders(java.util.List.of("*"));
                        return cors;
                });
                // TODO for authenticated routes set hasAuthority('user') or
                // hasAuthority('admin')
                http.csrf().disable().authorizeHttpRequests().anyRequest().permitAll().and().oauth2ResourceServer()
                                .jwt().jwtAuthenticationConverter(new KeycloakJwtAuthenticationConverter());
                http.oauth2Client().and().oauth2Login().tokenEndpoint().and().userInfoEndpoint();
                http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                return http.build();
        }
}
