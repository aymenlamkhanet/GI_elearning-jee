package com.example.Gii.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.time.LocalDateTime;

@Document(collection = "cours")
public class Cours extends DocumentPdf {

    @Getter
    @Setter
    private String duree;

    @Getter
    @Setter
    private String liens;

    public Cours() {
        super();
    }

    public Cours(String id, String titre, String description, String fichierId,
                 String niveau, String module, String duree, String liens , Long ratingAvg , LocalDateTime date) {
        super(id, titre, description, fichierId, niveau, module , ratingAvg , date);
        this.duree = duree;
        this.liens = liens;
    }



    @Override
    public String toString() {
        return "Cours{" +
                "duree='" + duree + '\'' +
                ", liens='" + liens + '\'' + // Updated to show as string
                "} " + super.toString();
    }
}
