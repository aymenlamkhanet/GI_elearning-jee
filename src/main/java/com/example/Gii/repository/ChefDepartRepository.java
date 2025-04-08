package com.example.Gii.repository;

import com.example.Gii.entity.ChefDepart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChefDepartRepository extends MongoRepository<ChefDepart, String> {
}
