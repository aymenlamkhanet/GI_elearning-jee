package com.example.Gii.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "reponses")
public class Reponse {
    @Id
    private String id;

    private String contenu;
    private LocalDateTime dateCreation;
    private String userId;
    private String userName;
    private Integer voteCount = 0;
    private Boolean isAccepted = false;
    private String questionId;

    public Reponse() {
    }

    public Reponse(String id, String contenu, LocalDateTime dateCreation, String userId,
                   String userName, Integer voteCount, Boolean isAccepted, String questionId) {
        this.id = id;
        this.contenu = contenu;
        this.dateCreation = dateCreation;
        this.userId = userId;
        this.userName = userName;
        this.voteCount = voteCount;
        this.isAccepted = isAccepted;
        this.questionId = questionId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Boolean getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(Boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    @Override
    public String toString() {
        return "Reponse{" +
                "id='" + id + '\'' +
                ", contenu='" + contenu + '\'' +
                ", dateCreation=" + dateCreation +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", voteCount=" + voteCount +
                ", isAccepted=" + isAccepted +
                ", questionId='" + questionId + '\'' +
                '}';
    }
}