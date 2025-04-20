package com.example.Gii.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "questions")
public class Question {
    @Setter
    @Getter
    @Id
    private String id;

    @Setter
    @Getter
    private String titre;
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
    private Integer answerCount = 0;
    @Setter
    @Getter
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

    @Getter
    @Setter
    @DBRef
    private Utilisateur utilisateur;

    @Getter
    @Setter
    @DBRef
    private List<Reponse> reponses;


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