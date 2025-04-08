package com.example.Gii.controller;

import com.example.Gii.entity.Reponse;
import com.example.Gii.service.ReponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reponses")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ReponseController {

    private final ReponseService reponseService;

    // Récupérer toutes les réponses
    @GetMapping("/all")
    public ResponseEntity<List<Reponse>> getAllReponses() {
        return ResponseEntity.ok(reponseService.getAllReponses());
    }

    // Ajouter une réponse
    @PostMapping("/add")
    public ResponseEntity<Reponse> addReponse(@RequestBody Reponse reponse) {
        return ResponseEntity.ok(reponseService.addReponse(reponse));
    }

    // Mettre à jour une réponse existante
    @PutMapping("/{id}")
    public ResponseEntity<Reponse> updateReponse(@PathVariable String id, @RequestBody Reponse reponseDetails) {
        return ResponseEntity.ok(reponseService.updateReponse(id, reponseDetails));
    }

    // Récupérer une réponse par ID
    @GetMapping("/{id}")
    public ResponseEntity<Reponse> getReponseById(@PathVariable String id) {
        return ResponseEntity.ok(reponseService.getReponseById(id));
    }

    // Supprimer une réponse par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReponse(@PathVariable String id) {
        reponseService.deleteReponse(id);
        return ResponseEntity.noContent().build();
    }

    // Compter le nombre total de réponses
    @GetMapping("/count")
    public ResponseEntity<Long> countReponses() {
        return ResponseEntity.ok(reponseService.countReponses());
    }
}
