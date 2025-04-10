package com.example.Gii.repository;

import com.example.Gii.entity.Reponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReponseRepository extends MongoRepository<Reponse, String> {
    List<Reponse> findByQuestionIdOrderByIsAcceptedDescVoteCountDescDateCreationAsc(String questionId);
    List<Reponse> findByUserIdOrderByDateCreationDesc(String userId);
    long countByQuestionId(String questionId);
}