package com.example.Gii.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "reponses")
public class Reponse {
    @Setter
    @Getter
    @Id
    private String id;

    @Setter
    @Getter
    private String contenu;
    @Setter
    @Getter
    private LocalDateTime dateCreation;
    @Setter
    @Getter
    private String userId;
    @Setter
    @Getter
    private String userName;
    @Setter
    @Getter
    private Integer voteCount = 0;
    @Setter
    @Getter
    private Boolean isAccepted = false;
    @Getter
    @Setter
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

    @Getter
    @Setter
    @DBRef
    private Utilisateur utilisateur;


    @Getter
    @Setter
    @DBRef
    private Question question;


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