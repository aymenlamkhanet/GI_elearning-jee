package com.example.Gii.service;

import com.example.Gii.entity.Commentaire;
import com.example.Gii.repository.CommentaireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentaireService {
    private final CommentaireRepository commentaireRepository;

    // Récupérer tous les commentaires
    public List<Commentaire> getAllCommentaires() {
        return commentaireRepository.findAll();
    }

    // Ajouter un commentaire
    public Commentaire addCommentaire(Commentaire commentaire) {
        commentaire.setDateCreation(LocalDateTime.now()); // Définir la date de création automatiquement
        return commentaireRepository.save(commentaire);
    }

    // Mettre à jour un commentaire existant
    public Commentaire updateCommentaire(String id, Commentaire commentaireDetails) {
        Optional<Commentaire> optionalCommentaire = commentaireRepository.findById(id);
        if (optionalCommentaire.isPresent()) {
            Commentaire commentaire = optionalCommentaire.get();
            commentaire.setContenu(commentaireDetails.getContenu());
            return commentaireRepository.save(commentaire);
        }
        throw new RuntimeException("Commentaire non trouvé avec l'ID : " + id);
    }

    // Récupérer un commentaire par ID
    public Commentaire getCommentaireById(String id) {
        return commentaireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commentaire non trouvé avec l'ID : " + id));
    }

    // Supprimer un commentaire par ID
    public void deleteCommentaire(String id) {
        commentaireRepository.deleteById(id);
    }

    // Rechercher des commentaires par contenu
    public List<Commentaire> findByContenuContaining(String contenu) {
        return commentaireRepository.findByContenuContaining(contenu);
    }

    // Compter le nombre total de commentaires
    public long countCommentaires() {
        return commentaireRepository.count();
    }
}
