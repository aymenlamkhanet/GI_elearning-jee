package com.example.Gii.repository;

import com.example.Gii.entity.ChefDepart;
import com.example.Gii.entity.Professeur;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface ChefDepartRepository extends MongoRepository<ChefDepart, String> {
    Optional<ChefDepart> findByEmail(String email);

}
