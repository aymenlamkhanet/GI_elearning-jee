package com.example.Gii.repository;


import com.example.Gii.entity.Professeur;
import com.example.Gii.entity.Utilisateur;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
import java.util.List;

public interface ProfesseurRepository extends MongoRepository<Professeur, String> {

    Optional<Professeur> findByEmail(String email);
    List<Professeur> findByModule(String module);
    List<Professeur> findTop5ByOrderByIdDesc();


}
