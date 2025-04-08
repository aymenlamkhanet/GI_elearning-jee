package com.example.Gii.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "professeurs")
public class Professeur extends Utilisateur {
    private String module;

    public Professeur(String id, String nom, String prenom, String email,
                      String motDePasse, String phone, String module) {
        super(id, nom, prenom, email, motDePasse, phone);
        this.module = module;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

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