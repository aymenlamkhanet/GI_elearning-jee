package com.example.Gii.controller;


import com.example.Gii.entity.Etudiant;
import com.example.Gii.service.EtudiantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/etudiant")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EtudiantController {
    private final EtudiantService etudiantService;

    // Récupérer tous les etudiants
    @GetMapping("/AllEtudiants")
    public ResponseEntity<List<Etudiant>> getAllEtudiants() {
        return ResponseEntity.ok(etudiantService.getAllEtudiants());
    }

    // Ajouter un etudiant
    @PostMapping("/addEtudiant")
    public ResponseEntity<Etudiant> addEtudiant(@RequestBody Etudiant etudiant) {
        Etudiant newEtudiant = etudiantService.AddEtudiant(etudiant);
        return ResponseEntity.ok(newEtudiant);
    }

    // Mettre à jour un etudiant existant
    @PutMapping("/{id}")

    public ResponseEntity<Etudiant> updateEtudiant(@PathVariable String id, @RequestBody Etudiant etudiantDetails) {
        System.out.println("user id " + id);
        System.out.println("data to change  " + etudiantDetails);
        Etudiant updatedEtudiant = etudiantService.updateUEtudiant(id, etudiantDetails);
        return ResponseEntity.ok(updatedEtudiant);
    }

    // Récupérer un etudiant par ID
    @GetMapping("/{id}")
    public ResponseEntity<Etudiant> getEtudiantById(@PathVariable String id) {
        Etudiant etudiant = etudiantService.getEtudiantById(id);
        return ResponseEntity.ok(etudiant);
    }

    // Supprimer un utilisateur par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEtudiant(@PathVariable String id) {
        etudiantService.deleteEtudiant(id);
        return ResponseEntity.noContent().build();
    }

    // Compter le nombre total d'etudiants
    @GetMapping("/count")
    public ResponseEntity<Long> countEtudiant() {
        return ResponseEntity.ok(etudiantService.countEtudiants());
    }





}
