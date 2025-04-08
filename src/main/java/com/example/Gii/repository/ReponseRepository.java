package com.example.Gii.repository;

import com.example.Gii.entity.Reponse;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReponseRepository extends MongoRepository<Reponse, String> {

    List<Reponse> findByContenu(String contenu);

    List<Reponse> findByDateCreation(LocalDateTime dateCreation);

}
