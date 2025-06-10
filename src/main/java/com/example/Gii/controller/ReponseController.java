package com.example.Gii.controller;

import com.example.Gii.entity.Reponse;
import com.example.Gii.service.ReponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/res")
public class ReponseController {

    private final ReponseService reponseService;

    @Autowired
    public ReponseController(ReponseService reponseService) {
        this.reponseService = reponseService;
    }

    @GetMapping("/questions/{questionId}/reponses")
    public ResponseEntity<List<Reponse>> getReponsesByQuestionId(@PathVariable String questionId) {
        return ResponseEntity.ok(reponseService.getReponsesByQuestionId(questionId));
    }

    @PostMapping("/questions/{questionId}/reponses")
    public ResponseEntity<Reponse> createReponse(@RequestBody Reponse reponse) {
        return new ResponseEntity<>(reponseService.createReponse(reponse), HttpStatus.CREATED);
    }

    @DeleteMapping("/reponses/{id}")
    public ResponseEntity<Void> deleteReponse(@PathVariable String id) {
        reponseService.deleteReponse(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reponses/{id}/upvote")
    public ResponseEntity<Reponse> upvoteReponse(@PathVariable String id) {
        Reponse reponse = reponseService.voteReponse(id, true);
        return reponse != null ? ResponseEntity.ok(reponse) : ResponseEntity.notFound().build();
    }

    @PostMapping("/reponses/{id}/downvote")
    public ResponseEntity<Reponse> downvoteReponse(@PathVariable String id) {
        Reponse reponse = reponseService.voteReponse(id, false);
        return reponse != null ? ResponseEntity.ok(reponse) : ResponseEntity.notFound().build();
    }

    @PostMapping("/questions/{questionId}/reponses/{id}/accept")
    public ResponseEntity<Reponse> acceptReponse(
            @PathVariable String id,
            @PathVariable String questionId) {
        Reponse reponse = reponseService.acceptReponse(id, questionId);
        return reponse != null ? ResponseEntity.ok(reponse) : ResponseEntity.notFound().build();
    }

    @GetMapping("/users/{userId}/reponses")
    public ResponseEntity<List<Reponse>> getUserReponses(@PathVariable String userId) {
        return ResponseEntity.ok(reponseService.getUserReponses(userId));
    }
}