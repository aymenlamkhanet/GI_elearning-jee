package com.example.Gii.security;

import com.example.Gii.entity.ChefDepart;
import com.example.Gii.entity.Etudiant;
import com.example.Gii.entity.Professeur;
import com.example.Gii.repository.ChefDepartRepository;
import com.example.Gii.repository.EtudiantRepository;
import com.example.Gii.repository.ProfesseurRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private ProfesseurRepository professeurRepository;

    @Autowired
    private ChefDepartRepository chefDepartRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Debug logging
        String requestPath = request.getServletPath();
        logger.info("Request path: " + requestPath);

        // Skip filtering for auth endpoints
        if (requestPath.startsWith("/auth/") ||
                requestPath.startsWith("/swagger-ui/") ||
                requestPath.startsWith("/v3/api-docs/")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authorizationHeader = request.getHeader("Authorization");
        logger.info("Authorization header: " + (authorizationHeader != null ? "present" : "missing"));

        String email = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                if (jwtUtil.validateToken(jwt)) {
                    email = jwtUtil.extractEmail(jwt);
                    logger.info("JWT valid for email: " + email);
                    String role = jwtUtil.extractRole(jwt);
                    logger.info("User role from token: " + role);
                } else {
                    logger.warn("JWT validation failed");
                }
            } catch (Exception e) {
                logger.error("Error validating token", e);
            }
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Try to find the user and determine their role
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            boolean userFound = false;

            // Check if it's a ChefDepartement (highest privilege)
            Optional<ChefDepart> chefDepartement = chefDepartRepository.findByEmail(email);
            if (chefDepartement.isPresent()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_CHEF_DEPART"));
                setAuthentication(request, email, authorities, chefDepartement.get().getId());
                logger.info("Authentication set for ChefDepart: " + email);
                userFound = true;
            }

            // Check if it's a Professeur (medium privilege)
            if (!userFound) {
                Optional<Professeur> professeur = professeurRepository.findByEmail(email);
                if (professeur.isPresent()) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_PROFESSEUR"));
                    setAuthentication(request, email, authorities, professeur.get().getId());
                    logger.info("Authentication set for Professeur: " + email);
                    userFound = true;
                }
            }

            // Check if it's an Etudiant (lowest privilege)
            if (!userFound) {
                Optional<Etudiant> etudiant = etudiantRepository.findByEmail(email);
                if (etudiant.isPresent()) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_ETUDIANT"));
                    setAuthentication(request, email, authorities, etudiant.get().getId());
                    logger.info("Authentication set for Etudiant: " + email);
                    userFound = true;
                }
            }

            if (!userFound) {
                logger.warn("User with email " + email + " not found in any repository");
            }
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(HttpServletRequest request, String email, List<SimpleGrantedAuthority> authorities, String userId) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                email, null, authorities
        );
        request.setAttribute("userId", userId);
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // Log the authorities that were set
        StringBuilder authLog = new StringBuilder("Authorities set: ");
        for (SimpleGrantedAuthority auth : authorities) {
            authLog.append(auth.getAuthority()).append(" ");
        }
        logger.info(authLog.toString());
    }
}