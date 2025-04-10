package com.example.Gii.repository;

import com.example.Gii.entity.Examen;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamenRepository extends MongoRepository<Examen, String> {
    List<Examen> findByNiveau(String niveau);
    List<Examen> findByModule(String module);
}