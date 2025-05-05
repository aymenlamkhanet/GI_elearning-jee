package com.example.Gii.repository;

import com.example.Gii.entity.Etudiant;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
import java.util.List;

public interface EtudiantRepository extends MongoRepository<Etudiant, String> {
    List<Etudiant> findByNiveau(String niveau);
    Optional<Etudiant> findByEmail(String email);
}

