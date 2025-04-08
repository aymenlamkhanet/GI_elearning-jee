package com.example.Gii.repository;

import com.example.Gii.entity.Commentaire;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentaireRepository extends MongoRepository<Commentaire, String> {
    List<Commentaire> findByContenu(String contenu);
    List<Commentaire> findByContenuContaining(String contenu);

    List<Commentaire> findByDateCreation(LocalDateTime dateCreation);
}
