package com.example.Gii.security;

import com.example.Gii.entity.Etudiant;
import com.example.Gii.entity.Professeur;
import com.example.Gii.entity.ChefDepart;
import com.example.Gii.repository.EtudiantRepository;
import com.example.Gii.repository.ProfesseurRepository;
import com.example.Gii.repository.ChefDepartRepository;
import io.jsonwebtoken.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "secret_key_123"; // Change to a strong secret key in production
    private final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private ProfesseurRepository professeurRepository;

    @Autowired
    private ChefDepartRepository chefDepartRepository;

    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();

        // Add user type to claims based on email
        Optional<Etudiant> etudiant = etudiantRepository.findByEmail(email);
        if (etudiant.isPresent()) {
            claims.put("role", "ROLE_ETUDIANT");
            claims.put("userId", etudiant.get().getId());
            claims.put("name", etudiant.get().getNom());
        }

        Optional<Professeur> professeur = professeurRepository.findByEmail(email);
        if (professeur.isPresent()) {
            claims.put("role", "ROLE_PROFESSEUR");
            claims.put("userId", professeur.get().getId());
            claims.put("name", professeur.get().getNom());
        }

        Optional<ChefDepart> chefDepart = chefDepartRepository.findByEmail(email);
        if (chefDepart.isPresent()){
            claims.put("role", "ROLE_CHEF_DEPART");
            claims.put("userId", chefDepart.get().getId());
            claims.put("name", chefDepart.get().getNom());
        }


        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String extractRole(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            return (String) claims.get("role");
        } catch (Exception e) {
            return null;
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}