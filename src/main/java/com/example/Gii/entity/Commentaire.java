package com.example.Gii.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "commentaires")
public class Commentaire {
    @Setter
    @Getter
    @Id
    private String id;

    @Setter
    @Getter
    private String contenu;
    @Setter
    @Getter
    private LocalDateTime dateCreation;


    public Commentaire(String id, String contenu, LocalDateTime dateCreation) {
        this.id = id;
        this.contenu = contenu;
        this.dateCreation = dateCreation;
    }

    @Getter
    @Setter
    @DBRef
    private Utilisateur utilisateur; // Auteur

    @Getter
    @Setter
    @DBRef
    private DocumentPdf document; // Document concerné


    @Override
    public String toString() {
        return "Commentaire{" +
                "id='" + id + '\'' +
                ", contenu='" + contenu + '\'' +
                ", dateCreation=" + dateCreation +
                '}';
    }
}
