package com.example.Gii.repository;

import com.example.Gii.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {
    Page<Question> findAllByOrderByDateCreationDesc(Pageable pageable);
    List<Question> findByUserIdOrderByDateCreationDesc(String userId);

    @Query("{'$or': [{'titre': {$regex: ?0, $options: 'i'}}, {'contenu': {$regex: ?0, $options: 'i'}}]}")
    Page<Question> searchQuestions(String keyword, Pageable pageable);

    Page<Question> findByOrderByVoteCountDesc(Pageable pageable);
    Page<Question> findByOrderByAnswerCountDesc(Pageable pageable);
}