package com.example.Gii.repository;

import com.example.Gii.entity.Exercice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciceRepository extends MongoRepository<Exercice, String> {
    List<Exercice> findByNiveau(String niveau);
    List<Exercice> findByModule(String module);
}