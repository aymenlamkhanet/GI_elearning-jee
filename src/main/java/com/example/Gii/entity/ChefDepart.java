package com.example.Gii.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chefs_depart")
public class ChefDepart extends Utilisateur {

    private String departement;

    public String getDepartement() {
        return departement;
    }

    // Corrected setter parameter name from 'id' to 'departement'
    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public ChefDepart(String id, String nom, String prenom, String email,
                      String motDePasse, String phone, String departement) {
        super(id, nom, prenom, email, motDePasse, phone);
        // Initialize the departement field
        this.departement = departement;
    }

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