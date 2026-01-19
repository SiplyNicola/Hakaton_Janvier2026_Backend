package com.example.hakaton_janvier2026_backend;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Indispensable pour tester les POST/PUT avec Swagger ou Postman
                .authorizeHttpRequests(auth -> auth
                        // 1. On autorise Swagger et la doc API sans authentification
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // 2. Pour le hackathon, vous pouvez aussi autoriser vos propres routes
                        .requestMatchers("/api/users/**").permitAll()

                        // 3. Le reste demande une connexion (ou vous pouvez tout mettre en permitAll() pour aller plus vite)
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true);
        return modelMapper;
    }
}
