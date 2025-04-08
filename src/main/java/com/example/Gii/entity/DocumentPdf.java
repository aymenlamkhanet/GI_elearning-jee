package com.example.Gii.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "documents")
public abstract class DocumentPdf {
    @Id
    private String id;
    private String titre;
    private String description;
    private String fichierId; // Lien HTTP vers le PDF
    private String niveau; // Gi1, Gi2, Gi3
    private String module;
    private long ratingAvg;
    private LocalDateTime date;

    public DocumentPdf(String id, String titre, String description, String fichierId, String niveau, String module) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.fichierId = fichierId;
        this.niveau = niveau;
        this.module = module;
    }

    public DocumentPdf(String id, String titre, String description, String fichierId, String niveau, String module,
                       long ratingAvg, LocalDateTime date) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.fichierId = fichierId;
        this.niveau = niveau;
        this.module = module;
        this.ratingAvg = ratingAvg;
        this.date = date;
    }

    public DocumentPdf() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFichierId() {
        return fichierId;
    }

    public void setFichierId(String fichierId) {
        this.fichierId = fichierId;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public long getRatingAvg() {
        return ratingAvg;
    }

    public void setRatingAvg(long ratingAvg) {
        this.ratingAvg = ratingAvg;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "DocumentPdf{" +
                "id='" + id + '\'' +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", fichierUrl='" + fichierId + '\'' +
                ", niveau='" + niveau + '\'' +
                ", module='" + module + '\'' +
                ", ratingAvg=" + ratingAvg +
                ", date=" + date +
                '}';
    }
}