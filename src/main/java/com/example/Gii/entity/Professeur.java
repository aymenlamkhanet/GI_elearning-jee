package com.example.Gii.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "professeurs")
public class Professeur extends Utilisateur {
    @Setter
    @Getter
    private String module;

    public Professeur(String id, String nom, String prenom, String email,
                      String motDePasse, String phone, String module) {
        super(id, nom, prenom, email, motDePasse, phone);
        this.module = module;
    }

    @Getter
    @Setter
    @DBRef
    private List<DocumentPdf> documentsAjoutes;


    @Override
    public String toString() {
        return "Professeur{" +
                "id='" + getId() + '\'' +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", module='" + module + '\'' +
                '}';
    }
}