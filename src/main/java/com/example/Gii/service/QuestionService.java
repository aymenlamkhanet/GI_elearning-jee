package com.example.Gii.service;

import com.example.Gii.entity.Question;
import com.example.Gii.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Page<Question> getAllQuestions(int page, int size) {
        return questionRepository.findAllByOrderByDateCreationDesc(PageRequest.of(page, size));
    }

    public Optional<Question> getQuestionById(String id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            Question q = question.get();
            q.setViewCount(q.getViewCount() + 1);
            questionRepository.save(q);
        }
        return question;
    }

    public Question createQuestion(Question question) {
        question.setDateCreation(LocalDateTime.now());
        question.setVoteCount(0);
        question.setAnswerCount(0);
        question.setViewCount(0);
        return questionRepository.save(question);
    }

    public void deleteQuestion(String id) {
        questionRepository.deleteById(id);
    }

    public Question voteQuestion(String id, boolean upvote) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        if (optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            if (upvote) {
                question.setVoteCount(question.getVoteCount() + 1);
            } else {
                question.setVoteCount(question.getVoteCount() - 1);
            }
            return questionRepository.save(question);
        }
        return null;
    }

    public Page<Question> searchQuestions(String keyword, int page, int size) {
        return questionRepository.searchQuestions(keyword, PageRequest.of(page, size));
    }

    public List<Question> getUserQuestions(String userId) {
        return questionRepository.findByUserIdOrderByDateCreationDesc(userId);
    }

    public void incrementAnswerCount(String questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        if (optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            question.setAnswerCount(question.getAnswerCount() + 1);
            questionRepository.save(question);
        }
    }

    public void decrementAnswerCount(String questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        if (optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            question.setAnswerCount(question.getAnswerCount() - 1);
            questionRepository.save(question);
        }
    }
}