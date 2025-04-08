package com.example.Gii.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "ouvrages")
public class Ouvrage extends DocumentPdf {

    private Long version;
    private String datePublication;
    private Long nbrPages;
    private String domaine;
    private String reviews;

    public Ouvrage() {
        super();
    }

    public Ouvrage(String id, String titre, String description, String fichierId, String niveau, String module,
                   Long version, String datePublication, Long nbrPages, String domaine, String reviews , Long ratingAvg , LocalDateTime date) {
        super(id, titre, description, fichierId, niveau, module ,ratingAvg , date);
        this.version = version;
        this.datePublication = datePublication;
        this.nbrPages = nbrPages;
        this.domaine = domaine;
        this.reviews = reviews;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(String datePublication) {
        this.datePublication = datePublication;
    }

    public Long getNbrPages() {
        return nbrPages;
    }

    public void setNbrPages(Long nbrPages) {
        this.nbrPages = nbrPages;
    }

    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "Ouvrage{" +
                "version=" + version +
                ", datePublication='" + datePublication + '\'' +
                ", nbrPages=" + nbrPages +
                ", domaine='" + domaine + '\'' +
                ", reviews='" + reviews + '\'' +
                "} " + super.toString();
    }
}
