package com.descenty.work_in_spring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // configure CORS allowed origins
        return http.cors().configurationSource(request -> {
            var cors = new org.springframework.web.cors.CorsConfiguration();
            cors.setAllowedOrigins(java.util.List.of("http://localhost:19006"));
            // cors.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE"));
            // cors.setAllowedHeaders(java.util.List.of("*"));
            return cors;
        }).and().csrf().disable().authorizeHttpRequests().anyRequest().permitAll().and().build();
    }
}
