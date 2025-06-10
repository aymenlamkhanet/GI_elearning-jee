package com.example.Gii.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.time.Period;

@Document(collection = "etudiants")
public class Etudiant extends Utilisateur {
    private String niveau;
    private int fireScore = 0;                     // Current score
    private LocalDate lastActiveDate;              // Last activity date
    private LocalDate accountCreationDate;         // Account creation date
    private int contentInteractions = 0;           // Total resource interactions
    private int forumContributions = 0;            // Forum posts/comments

    public Etudiant(String id, String nom, String prenom, String email,
                    String motDePasse, String phone, String niveau) {
        super(id, nom, prenom, email, motDePasse, phone);
        this.niveau = niveau;
        this.accountCreationDate = LocalDate.now();  // Set creation date on new account
        this.lastActiveDate = LocalDate.now();        // Initialize last active date
    }

    public Etudiant() {
        super();
        this.accountCreationDate = LocalDate.now();
        this.lastActiveDate = LocalDate.now();
    }

    // Add these new methods for score management
    public void updateFireScore(int points) {
        this.fireScore += points;
    }

    public void recordInteraction() {
        this.contentInteractions++;
        updateFireScore(1);  // 1 point per interaction
        this.lastActiveDate = LocalDate.now();
    }

    public void recordForumContribution() {
        this.forumContributions++;
        updateFireScore(3);  // 3 points per forum action
        this.lastActiveDate = LocalDate.now();
    }

    public void checkDailyLoginBonus() {
        if (!LocalDate.now().equals(lastActiveDate)) {
            updateFireScore(2);  // 2 points for daily login
            this.lastActiveDate = LocalDate.now();
        }
    }

    public int calculateAccountAge() {
        return Period.between(accountCreationDate, LocalDate.now()).getDays();
    }

    // Getters and setters for new attributes
    public int getFireScore() {
        return fireScore;
    }

    public void setFireScore(int fireScore) {
        this.fireScore = fireScore;
    }

    public LocalDate getLastActiveDate() {
        return lastActiveDate;
    }

    public void setLastActiveDate(LocalDate lastActiveDate) {
        this.lastActiveDate = lastActiveDate;
    }

    public LocalDate getAccountCreationDate() {
        return accountCreationDate;
    }

    public void setAccountCreationDate(LocalDate accountCreationDate) {
        this.accountCreationDate = accountCreationDate;
    }

    public int getContentInteractions() {
        return contentInteractions;
    }

    public void setContentInteractions(int contentInteractions) {
        this.contentInteractions = contentInteractions;
    }

    public int getForumContributions() {
        return forumContributions;
    }

    public void setForumContributions(int forumContributions) {
        this.forumContributions = forumContributions;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    // Update toString() to include new fields
    @Override
    public String toString() {
        return "Etudiant{" +
                "id='" + getId() + '\'' +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", niveau='" + niveau + '\'' +
                ", fireScore=" + fireScore +
                ", lastActiveDate=" + lastActiveDate +
                ", accountCreationDate=" + accountCreationDate +
                ", contentInteractions=" + contentInteractions +
                ", forumContributions=" + forumContributions +
                '}';
    }
}