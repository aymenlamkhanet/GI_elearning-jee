package com.example.Gii.controller;

import com.example.Gii.entity.Commentaire;
import com.example.Gii.service.CommentaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commentaire")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CommentaireController {
    private final CommentaireService commentaireService;

    // Récupérer tous les commentaires
    @GetMapping("/AllCommentaires")
    public ResponseEntity<List<Commentaire>> getAllCommentaires() {
        return ResponseEntity.ok(commentaireService.getAllCommentaires());
    }

    // Ajouter un commentaire
    @PostMapping("/AddCommentaire")
    public ResponseEntity<Commentaire> addCommentaire(@RequestBody Commentaire commentaire) {
        Commentaire newCommentaire = commentaireService.addCommentaire(commentaire);
        return ResponseEntity.ok(newCommentaire);
    }

    // Mettre à jour un commentaire existant
    @PutMapping("/{id}")
    public ResponseEntity<Commentaire> updateCommentaire(@PathVariable String id, @RequestBody Commentaire commentaireDetails) {
        Commentaire updatedCommentaire = commentaireService.updateCommentaire(id, commentaireDetails);
        return ResponseEntity.ok(updatedCommentaire);
    }

    // Récupérer un commentaire par ID
    @GetMapping("/{id}")
    public ResponseEntity<Commentaire> getCommentaireById(@PathVariable String id) {
        Commentaire commentaire = commentaireService.getCommentaireById(id);
        return ResponseEntity.ok(commentaire);
    }

    // Supprimer un commentaire par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommentaire(@PathVariable String id) {
        commentaireService.deleteCommentaire(id);
        return ResponseEntity.noContent().build();
    }

    // Rechercher des commentaires par contenu
    @GetMapping("/search")
    public ResponseEntity<List<Commentaire>> searchCommentaires(@RequestParam String contenu) {
        return ResponseEntity.ok(commentaireService.findByContenuContaining(contenu));
    }

    // Compter le nombre total de commentaires
    @GetMapping("/count")
    public ResponseEntity<Long> countCommentaires() {
        return ResponseEntity.ok(commentaireService.countCommentaires());
    }
}
