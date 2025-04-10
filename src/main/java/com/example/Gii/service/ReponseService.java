package com.example.Gii.service;

import com.example.Gii.entity.Reponse;
import com.example.Gii.repository.ReponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReponseService {

    private final ReponseRepository reponseRepository;
    private final QuestionService questionService;

    @Autowired
    public ReponseService(ReponseRepository reponseRepository, QuestionService questionService) {
        this.reponseRepository = reponseRepository;
        this.questionService = questionService;
    }

    public List<Reponse> getReponsesByQuestionId(String questionId) {
        return reponseRepository.findByQuestionIdOrderByIsAcceptedDescVoteCountDescDateCreationAsc(questionId);
    }

    public Reponse createReponse(Reponse reponse) {
        reponse.setDateCreation(LocalDateTime.now());
        reponse.setVoteCount(0);
        reponse.setIsAccepted(false);
        Reponse savedReponse = reponseRepository.save(reponse);

        questionService.incrementAnswerCount(reponse.getQuestionId());
        return savedReponse;
    }

    public void deleteReponse(String id) {
        Optional<Reponse> optionalReponse = reponseRepository.findById(id);
        if (optionalReponse.isPresent()) {
            String questionId = optionalReponse.get().getQuestionId();
            reponseRepository.deleteById(id);
            questionService.decrementAnswerCount(questionId);
        }
    }

    public Reponse voteReponse(String id, boolean upvote) {
        Optional<Reponse> optionalReponse = reponseRepository.findById(id);
        if (optionalReponse.isPresent()) {
            Reponse reponse = optionalReponse.get();
            if (upvote) {
                reponse.setVoteCount(reponse.getVoteCount() + 1);
            } else {
                reponse.setVoteCount(reponse.getVoteCount() - 1);
            }
            return reponseRepository.save(reponse);
        }
        return null;
    }

    public Reponse acceptReponse(String id, String questionId) {
        // First unaccept any previously accepted answers
        List<Reponse> reponses = reponseRepository.findByQuestionIdOrderByIsAcceptedDescVoteCountDescDateCreationAsc(questionId);
        for (Reponse r : reponses) {
            if (r.getIsAccepted()) {
                r.setIsAccepted(false);
                reponseRepository.save(r);
            }
        }

        // Accept the new answer
        Optional<Reponse> optionalReponse = reponseRepository.findById(id);
        if (optionalReponse.isPresent()) {
            Reponse reponse = optionalReponse.get();
            reponse.setIsAccepted(true);
            return reponseRepository.save(reponse);
        }
        return null;
    }

    public List<Reponse> getUserReponses(String userId) {
        return reponseRepository.findByUserIdOrderByDateCreationDesc(userId);
    }
}