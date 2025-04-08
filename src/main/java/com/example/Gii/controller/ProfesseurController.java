package com.example.Gii.controller;

import com.example.Gii.entity.Professeur;
import com.example.Gii.service.ProfesseurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professeur")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProfesseurController {
    private final ProfesseurService professeurService;

    // Récupérer tous les professeurs
    @GetMapping("/AllProfesseurs")
    public ResponseEntity<List<Professeur>> getAllProfesseurs() {
        return ResponseEntity.ok(professeurService.getAllProfesseurs());
    }

    // Ajouter un professeur
    @PostMapping("/addProfesseur")
    public ResponseEntity<Professeur> addProfesseur(@RequestBody Professeur professeur) {
        Professeur newProfesseur = professeurService.addProfesseur(professeur);
        return ResponseEntity.ok(newProfesseur);
    }

    // Mettre à jour un professeur existant
    @PutMapping("/{id}")
    public ResponseEntity<Professeur> updateProfesseur(@PathVariable String id, @RequestBody Professeur professeurDetails) {
        System.out.println("user id " + id);
        System.out.println("data to change  " + professeurDetails);
        Professeur updatedProfesseur = professeurService.updateProfesseur(id, professeurDetails);
        return ResponseEntity.ok(updatedProfesseur);
    }

    // Récupérer un professeur par ID
    @GetMapping("/{id}")
    public ResponseEntity<Professeur> getProfesseurById(@PathVariable String id) {
        Professeur professeur = professeurService.getProfesseurById(id);
        return ResponseEntity.ok(professeur);
    }

    // Supprimer un professeur par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfesseur(@PathVariable String id) {
        professeurService.deleteProfesseur(id);
        return ResponseEntity.noContent().build();
    }

    // Compter le nombre total de professeurs
    @GetMapping("/count")
    public ResponseEntity<Long> countProfesseur() {
        return ResponseEntity.ok(professeurService.countProfesseurs());
    }
}
