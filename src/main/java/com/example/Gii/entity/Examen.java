package com.example.Gii.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "examens")
public class Examen extends DocumentPdf {
    public Examen(String id, String titre, String description, String fichierUrl, String niveau, String module) {
        super(id, titre, description, fichierUrl, niveau, module);
    }
}
