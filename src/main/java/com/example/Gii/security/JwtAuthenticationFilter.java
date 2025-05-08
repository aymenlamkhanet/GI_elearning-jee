package com.example.Gii.security;

import com.example.Gii.entity.Etudiant;
import com.example.Gii.entity.Professeur;
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
import com.example.Gii.entity.ChefDepart;
import com.example.Gii.repository.ChefDepartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    // You can also add ChefDepartementRepository when you create it
    // @Autowired
    // private ChefDepartementRepository chefDepartementRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String email = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                if (jwtUtil.validateToken(jwt)) {
                    email = jwtUtil.extractEmail(jwt);
                }
            } catch (Exception e) {
                logger.error("Error validating token", e);
            }
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Try to find the user and determine their role
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();

            // Check if it's an Etudiant
            Optional<Etudiant> etudiant = etudiantRepository.findByEmail(email);
            if (etudiant.isPresent()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_ETUDIANT"));
                setAuthentication(request, email, authorities);
                filterChain.doFilter(request, response);
                return;
            }

            // Check if it's a Professeur
            Optional<Professeur> professeur = professeurRepository.findByEmail(email);
            if (professeur.isPresent()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_PROFESSEUR"));
                setAuthentication(request, email, authorities);
                filterChain.doFilter(request, response);
                return;
            }

            // Check if it's a ChefDepartement
            Optional<ChefDepart> chefDepartement = chefDepartRepository.findByEmail(email);
            if (chefDepartement.isPresent()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_CHEF_DEPART"));
                setAuthentication(request, email, authorities);
                filterChain.doFilter(request, response);
                return;
            }

        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(HttpServletRequest request, String email, List<SimpleGrantedAuthority> authorities) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                email, null, authorities
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}