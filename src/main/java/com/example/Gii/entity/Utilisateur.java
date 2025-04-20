package com.example.Gii.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(collection = "utilisateurs")
public abstract class Utilisateur {

    @Setter
    @Getter
    @Id
    private String id;
    @Setter
    @Getter
    private String nom;
    @Setter
    @Getter
    private String prenom;
    @Setter
    @Getter
    private String email;
    @Setter
    @Getter
    private String motDePasse;
    @Setter
    @Getter
    private  String phone;
    public Utilisateur(String id, String nom, String prenom, String email, String motDePasse, String phone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.phone = phone;

    }

    @Getter
    @Setter
    @DBRef
    private List<Commentaire> commentaires;

    @Getter
    @Setter
    @DBRef
    private List<Question> questions;

    @Getter
    @Setter
    @DBRef
    private List<Reponse> reponses;



    @Override
    public String toString() {
        return "Utilisateur{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
