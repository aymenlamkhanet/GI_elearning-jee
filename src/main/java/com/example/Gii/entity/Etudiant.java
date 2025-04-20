package com.example.Gii.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "etudiants")
public class Etudiant extends Utilisateur {
    @Setter
    @Getter
    private String niveau;  // Attribut spécifique : GI1, GI2 ou GI3

    public Etudiant(String id, String nom, String prenom, String email,
                    String motDePasse, String phone, String niveau) {
        super(id, nom, prenom, email, motDePasse, phone);
        this.niveau = niveau;
    }

    @Getter
    @Setter
    @DBRef
    private List<DocumentPdf> documentsConsultes;


    @Override
    public String toString() {
        return "Etudiant{" +
                "id='" + getId() + '\'' +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", niveau='" + niveau + '\'' +
                '}';
    }
}