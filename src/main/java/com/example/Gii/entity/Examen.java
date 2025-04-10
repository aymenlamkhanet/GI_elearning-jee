package com.example.Gii.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "examens")
public class Examen extends DocumentPdf {

    public Examen() {
        super();
    }
    public Examen(String id, String titre, String description, String fichierUrl, String niveau, String module) {
        super(id, titre, description, fichierUrl, niveau, module);
    }

    public Examen(String id, String titre, String description, String fichierUrl, String niveau, String module,
                    long ratingAvg, LocalDateTime date) {
        super(id, titre, description, fichierUrl, niveau, module, ratingAvg, date);
    }
}
