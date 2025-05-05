package com.example.Gii.controller;

public class AuthRequest {
    private String email;
    private String motDePasse;
    private String niveau;     // Added niveau
    private String nom;        // Added nom
    private String prenom;     // Added prenom
    private String phone;
    private String module;
    private String departement;

    public String getEmail() {
        return email;
    }

    public String getModule(){
        return module;
    }

    public void setModule(String module){
        this.module = module;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}