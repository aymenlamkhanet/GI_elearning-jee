package com.example.Gii.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "ouvrages")
public class Ouvrage extends DocumentPdf {

    @Getter
    @Setter
    private Long version;

    @Getter
    @Setter
    private String datePublication;

    @Getter
    @Setter
    private Long nbrPages;

    @Getter
    @Setter
    private String domaine;

    @Getter
    @Setter
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
