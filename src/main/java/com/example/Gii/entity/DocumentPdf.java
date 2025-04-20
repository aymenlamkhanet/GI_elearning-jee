package com.example.Gii.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "documents")
public abstract class DocumentPdf {
    @Setter
    @Getter
    @Id
    private String id;
    @Setter
    @Getter
    private String titre;
    @Setter
    @Getter
    private String description;
    @Setter
    @Getter
    private String fichierId; // Lien HTTP vers le PDF
    @Setter
    @Getter
    private String niveau; // Gi1, Gi2, Gi3
    @Setter
    @Getter
    private String module;
    @Setter
    @Getter
    private long ratingAvg;
    @Setter
    @Getter
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

    @Getter
    @Setter
    @DBRef
    private Professeur professeur; // Ajouté par

    @Getter
    @Setter
    @DBRef
    private ChefDepart chefDepart; // Gestionné par

    @Getter
    @Setter
    @DBRef
    private List<Etudiant> consultéPar; // Liste d'étudiants qui l'ont consulté

    @Getter
    @Setter
    @DBRef
    private List<Commentaire> commentaires; // Liste des commentaires associés


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