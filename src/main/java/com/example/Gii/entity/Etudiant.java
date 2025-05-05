package com.example.Gii.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "etudiants")
public class Etudiant extends Utilisateur {
    private String niveau;  // Attribut spécifique : GI1, GI2 ou GI3

    public Etudiant(String id, String nom, String prenom, String email,
                    String motDePasse, String phone, String niveau) {
        super(id, nom, prenom, email, motDePasse, phone);
        this.niveau = niveau;
    }

    public Etudiant() {
        super();
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

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