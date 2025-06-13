package com.example.Gii.config;

import com.example.Gii.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers(
                                "/auth/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // Student-specific endpoints
                        .requestMatchers(HttpMethod.GET, "/api/etudiant/**")
                        .hasAnyAuthority("ROLE_ETUDIANT", "ROLE_PROFESSEUR", "ROLE_CHEF_DEPART")
                        .requestMatchers(HttpMethod.PUT, "/api/etudiant/**")
                        .hasAnyAuthority("ROLE_ETUDIANT", "ROLE_CHEF_DEPART")

                        // Professor-specific personal endpoints
                        .requestMatchers(HttpMethod.GET, "/api/professeur/**")
                        .hasAnyAuthority("ROLE_PROFESSEUR", "ROLE_CHEF_DEPART")
                        .requestMatchers(HttpMethod.PUT, "/api/professeur/**")
                        .hasAnyAuthority("ROLE_PROFESSEUR", "ROLE_CHEF_DEPART")

                        // Teaching resources endpoints - allow students to view
                        .requestMatchers(HttpMethod.GET,
                                "/api/exercices/**", "/api/cours/**", "/api/examens/**",
                                "/api/exercise/**", "/api/cours/**", "/api/examen/**") // support both plural forms
                        .hasAnyAuthority("ROLE_ETUDIANT", "ROLE_PROFESSEUR", "ROLE_CHEF_DEPART")

                        // Teaching resources modification - professors only
                        .requestMatchers(HttpMethod.POST,
                                "/api/exercices/**", "/api/cours/**", "/api/examens/**",
                                "/api/exercise/**", "/api/cours/**", "/api/examen/**")
                        .hasAnyAuthority("ROLE_PROFESSEUR", "ROLE_CHEF_DEPART")
                        .requestMatchers(HttpMethod.PUT,
                                "/api/exercices/**", "/api/cours/**", "/api/examens/**",
                                "/api/exercise/**", "/api/cours/**", "/api/examen/**")
                        .hasAnyAuthority("ROLE_PROFESSEUR", "ROLE_CHEF_DEPART")
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/exercices/**", "/api/cours/**", "/api/examens/**",
                                "/api/exercise/**", "/api/cours/**", "/api/examen/**")
                        .hasAnyAuthority("ROLE_PROFESSEUR", "ROLE_CHEF_DEPART")

                        // Comments, questions, resources - public (as per your config)
                        .requestMatchers("/api/commentaire/**","/api/questions/**","/api/res/**").permitAll()

                        // Chef Department general access
                        .requestMatchers(HttpMethod.GET, "/api/**")
                        .hasAnyAuthority("ROLE_CHEF_DEPART","ROLE_PROFESSEUR","ROLE_ETUDIANT")
                        .requestMatchers(HttpMethod.POST, "/api/**")
                        .hasAnyAuthority("ROLE_CHEF_DEPART")
                        .requestMatchers(HttpMethod.PUT, "/api/**")
                        .hasAnyAuthority("ROLE_CHEF_DEPART")
                        .requestMatchers(HttpMethod.DELETE, "/api/**")
                        .hasAnyAuthority("ROLE_CHEF_DEPART")

                        // Any other request
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000")); // Your frontend URL
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
        config.setExposedHeaders(Arrays.asList("Authorization"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}