package com.example.Gii.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "questions")
public class Question {
    @Id
    private String id;

    private String titre;
    private String contenu;
    private LocalDateTime dateCreation;
    private String userId;
    private String userName;
    private Integer voteCount = 0;
    private Integer answerCount = 0;
    private Integer viewCount = 0;

    public Question() {
    }

    public Question(String id, String titre, String contenu, LocalDateTime dateCreation,
                    String userId, String userName, Integer voteCount, Integer answerCount, Integer viewCount) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
        this.dateCreation = dateCreation;
        this.userId = userId;
        this.userName = userName;
        this.voteCount = voteCount;
        this.answerCount = answerCount;
        this.viewCount = viewCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(Integer answerCount) {
        this.answerCount = answerCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", titre='" + titre + '\'' +
                ", contenu='" + contenu + '\'' +
                ", dateCreation=" + dateCreation +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", voteCount=" + voteCount +
                ", answerCount=" + answerCount +
                ", viewCount=" + viewCount +
                '}';
    }
}