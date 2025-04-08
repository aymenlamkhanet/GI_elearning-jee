package com.example.Gii.repository;


import com.example.Gii.entity.Professeur;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProfesseurRepository extends MongoRepository<Professeur, String> {

    List<Professeur> findByModule(String module);


}
