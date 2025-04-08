package com.example.Gii.repository;

import com.example.Gii.entity.Ouvrage;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;


public interface OuvrageRepository extends MongoRepository<Ouvrage, String> {
    List<Ouvrage> findByNiveau(String niveau);
    List<Ouvrage> findByModule(String module);

    @Query(value = "{}", fields = "{module : 1}")
    List<Ouvrage> findAllModules();

    @Aggregation("{ $group: { _id: '$module' } }")
    List<String> findDistinctModules();

}