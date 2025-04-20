package com.example.Gii.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "chefs_depart")
public class ChefDepart extends Utilisateur {

    // Corrected setter parameter name from 'id' to 'departement'
    @Setter
    @Getter
    private String departement;

    public ChefDepart(String id, String nom, String prenom, String email,
                      String motDePasse, String phone, String departement) {
        super(id, nom, prenom, email, motDePasse, phone);
        // Initialize the departement field
        this.departement = departement;
    }

    @Getter
    @Setter
    @DBRef
    private List<DocumentPdf> documentsGeres;


    @Override
    public String toString() {
        return "ChefDepart{" +
                "id='" + getId() + '\'' +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", departement='" + getDepartement() + '\'' +
                '}';
    }
}