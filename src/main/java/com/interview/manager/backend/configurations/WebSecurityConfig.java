package com.interview.manager.backend.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private static final String ALLOWED_ORIGIN = System.getProperty("ALLOWED_ORIGIN", "https://gvigai.devbstaging.com");
    private static final List<String> ALLOWED_METHODS = List.of("GET", "POST", "PATCH", "DELETE");

    @Autowired
    private JwtTokenVerifier jwtTokenVerifier;

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/**").authenticated()
                .requestMatchers(HttpMethod.PATCH, "/api/v1/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/v1/**").authenticated()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtTokenVerifier, UsernamePasswordAuthenticationFilter.class)
            )

            .formLogin(formLogin -> formLogin
                .loginProcessingUrl("/login").permitAll())
            .logout(logout -> logout
                .logoutUrl("/logout").permitAll());

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(ALLOWED_ORIGIN));
        configureCommonCORS(configuration);
        return buildCorsConfigurationSource(configuration);

    }

    private void configureCommonCORS(CorsConfiguration configuration) {
        configuration.setAllowedMethods(ALLOWED_METHODS);
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true);
    }

    private CorsConfigurationSource buildCorsConfigurationSource(CorsConfiguration configuration) {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/v1/**", configuration);
        return source;
    }

}
