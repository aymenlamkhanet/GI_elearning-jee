package com.example.Gii.controller;

import com.example.Gii.entity.ChefDepart;
import com.example.Gii.service.ChefDepartService;
import com.example.Gii.repository.ChefDepartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/chef")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ChefDepartController {
    private final ChefDepartService chefDepartService;

    @Autowired
    private ChefDepartRepository chefDepartRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Récupérer tous les chefs de département
    @GetMapping("/AllChefDeparts")
    public ResponseEntity<List<ChefDepart>> getAllChefDeparts() {
        return ResponseEntity.ok(chefDepartService.getAllChefsDepart());
    }

    // Ajouter un chef de département
    @PostMapping("/addChefDepart")
    public ResponseEntity<ChefDepart> addChefDepart(@RequestBody ChefDepart chefDepart) {
        ChefDepart newChef = chefDepartService.addChefDepart(chefDepart);
        return ResponseEntity.ok(newChef);
    }

    // Mettre à jour un chef de département existant
    @PutMapping("/{id}")
    public ResponseEntity<ChefDepart> updateChefDepart(@PathVariable String id, @RequestBody ChefDepart chefDepartDetails) {
        System.out.println("user id " + id);
        System.out.println("data to change  " + chefDepartDetails);
        ChefDepart updatedChef = chefDepartService.updateChefDepart(id, chefDepartDetails);
        return ResponseEntity.ok(updatedChef);
    }

    // Récupérer un chef de département par ID
    @GetMapping("/{id}")
    public ResponseEntity<ChefDepart> getChefDepartById(@PathVariable String id) {
        ChefDepart chefDepart = chefDepartService.getChefDepartById(id);
        return ResponseEntity.ok(chefDepart);
    }

    // Supprimer un chef de département par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChefDepart(@PathVariable String id) {
        chefDepartService.deleteChefDepart(id);
        return ResponseEntity.noContent().build();
    }

    // Compter le nombre total de chefs de département
    @GetMapping("/count")
    public ResponseEntity<Long> countChefDepart() {
        return ResponseEntity.ok(chefDepartService.countChefsDepart());
    }

    // Endpoint pour changer le mot de passe
    @PutMapping("/{id}/change-password")
    public ResponseEntity<?> changePassword(
            @PathVariable String id,
            @RequestBody Map<String, String> passwordRequest) {

        String newPassword = passwordRequest.get("newPassword");
        String confirmPassword = passwordRequest.get("confirmPassword");

        if (newPassword == null || confirmPassword == null) {
            return ResponseEntity.badRequest().body("New password and confirmation are required");
        }

        // Check if the new password and confirmation match
        if (!newPassword.equals(confirmPassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Les mots de passe ne correspondent pas");
        }

        try {
            // Find the chef by ID
            Optional<ChefDepart> chefOptional = chefDepartRepository.findById(id);
            if (!chefOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chef de département non trouvé");
            }

            ChefDepart chef = chefOptional.get();

            // Hash the new password and update it
            chef.setMotDePasse(passwordEncoder.encode(newPassword));

            // Save the updated chef
            ChefDepart updatedChef = chefDepartRepository.save(chef);

            // Return success response without password
            Map<String, Object> response = new HashMap<>();
            response.put("id", updatedChef.getId());
            response.put("message", "Password updated successfully");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while changing password: " + e.getMessage());
        }
    }
}