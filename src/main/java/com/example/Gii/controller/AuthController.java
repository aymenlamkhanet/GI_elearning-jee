package com.example.Gii.controller;

import com.example.Gii.entity.Etudiant;
import com.example.Gii.entity.Professeur;
import com.example.Gii.entity.ChefDepart;
import com.example.Gii.repository.EtudiantRepository;
import com.example.Gii.repository.ProfesseurRepository;
import com.example.Gii.repository.UtilisateurRepository;
import com.example.Gii.repository.ChefDepartRepository;
import com.example.Gii.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private ProfesseurRepository professeurRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private ChefDepartRepository chefDepartRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public ResponseEntity<Object> login(@RequestBody AuthRequest authRequest) {
        Optional<Etudiant> etudiant = etudiantRepository.findByEmail(authRequest.getEmail());
        Optional<Professeur> professeur = professeurRepository.findByEmail(authRequest.getEmail());
        Optional<ChefDepart> chefDepart = chefDepartRepository.findByEmail(authRequest.getEmail());

        // In AuthController.java, modify the login response
        // In AuthController.java
        if (etudiant.isPresent() && passwordEncoder.matches(authRequest.getMotDePasse(), etudiant.get().getMotDePasse())) {
            String token = jwtUtil.generateToken(authRequest.getEmail());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("id", etudiant.get().getId());
            response.put("nom", etudiant.get().getNom());
            response.put("type", "etudiant");
            response.put("role", "ROLE_ETUDIANT");
            response.put("fireScore", etudiant.get().getFireScore());
            response.put("contentInteractions", etudiant.get().getContentInteractions());
            response.put("forumContributions", etudiant.get().getForumContributions());
            response.put("accountCreationDate", etudiant.get().getAccountCreationDate());
            response.put("lastActiveDate", etudiant.get().getLastActiveDate());


            return ResponseEntity.ok(response);
        }
        else if (professeur.isPresent() && passwordEncoder.matches(authRequest.getMotDePasse(), professeur.get().getMotDePasse())) {
            String token = jwtUtil.generateToken(authRequest.getEmail());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("id", professeur.get().getId());
            response.put("nom", professeur.get().getNom());
            response.put("type", "professeur");
            response.put("role", "ROLE_PROFESSEUR");

            return ResponseEntity.ok(response);
        }
        else if (chefDepart.isPresent() && passwordEncoder.matches(authRequest.getMotDePasse(), chefDepart.get().getMotDePasse())) {
            String token = jwtUtil.generateToken(authRequest.getEmail());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("id", chefDepart.get().getId());
            response.put("nom", chefDepart.get().getNom());
            response.put("type", "chef_departement");
            response.put("role", "ROLE_CHEF_DEPART");

            return ResponseEntity.ok(response);
        }


        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody AuthRequest authRequest) {
        // Check if the email already exists
        Optional<Etudiant> etudiant = etudiantRepository.findByEmail(authRequest.getEmail());

        if (etudiant.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
        }

        // Create a new Etudiant (which is a subclass of Utilisateur)
        Etudiant newEtudiant = new Etudiant();
        newEtudiant.setEmail(authRequest.getEmail());
        newEtudiant.setMotDePasse(passwordEncoder.encode(authRequest.getMotDePasse()));
        newEtudiant.setNiveau(authRequest.getNiveau());
        newEtudiant.setNom(authRequest.getNom());
        newEtudiant.setPrenom(authRequest.getPrenom());
        newEtudiant.setPhone(authRequest.getPhone());

        // Save the Etudiant object as a Utilisateur
        etudiantRepository.save(newEtudiant);

        return ResponseEntity.ok("Etudiant registered successfully");
    }

    @PostMapping("/sign-up-prof")
    public ResponseEntity<String> signUpProf(@RequestBody AuthRequest authRequest) {
        // Check if the email already exists
        Optional<Professeur> professeur = professeurRepository.findByEmail(authRequest.getEmail());

        if (professeur.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
        }

        // Create a new Prof (which is a subclass of Utilisateur)
        Professeur newProfesseur = new Professeur(
                null, // Assuming the ID is generated by MongoDB, so it's set to null
                authRequest.getNom(),
                authRequest.getPrenom(),
                authRequest.getEmail(),
                passwordEncoder.encode(authRequest.getMotDePasse()),
                authRequest.getPhone(),
                authRequest.getModule() // Assuming the module is provided in the AuthRequest
        );

        // Save the Prof object as a Utilisateur
        professeurRepository.save(newProfesseur);

        return ResponseEntity.ok("Prof registered successfully");
    }
}