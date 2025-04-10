package com.example.Gii.controller;

import com.example.Gii.entity.Examen;
import com.example.Gii.service.ExamenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/examens")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ExamenController {
    private final ExamenService examenService;

    // Récupérer tous les examens
    @GetMapping
    public ResponseEntity<List<Examen>> getAllExamens() {
        return ResponseEntity.ok(examenService.getAllExamens());
    }

    // Récupérer un examen par ID
    @GetMapping("/{id}")
    public ResponseEntity<Examen> getExamenById(@PathVariable String id) {
        Optional<Examen> examen = examenService.getExamenById(id);
        return examen.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Ajouter un examen avec fichier PDF
    @PostMapping("/ajouter")
    public ResponseEntity<Examen> ajouterExamen(@RequestParam("titre") String titre,
                                                @RequestParam("description") String description,
                                                @RequestParam("niveau") String niveau,
                                                @RequestParam("module") String module,
                                                @RequestParam("ratingAvg") long ratingAvg,
                                                @RequestParam(value = "date", required = false) LocalDateTime date,
                                                @RequestParam("file") MultipartFile file) {
        try {
            Examen examen = new Examen();
            examen.setTitre(titre);
            examen.setDescription(description);
            examen.setNiveau(niveau);
            examen.setModule(module);
            examen.setRatingAvg(ratingAvg);
            examen.setDate(date != null ? date : LocalDateTime.now()); // Use provided date or current time

            Examen savedExamen = examenService.ajouterExamenAvecPdf(examen, file);
            return ResponseEntity.ok(savedExamen);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // Mettre à jour un examen
    @PutMapping("/update/{id}")
    public ResponseEntity<Examen> updateExamen(@PathVariable String id,
                                               @RequestParam("titre") String titre,
                                               @RequestParam("description") String description,
                                               @RequestParam("niveau") String niveau,
                                               @RequestParam("module") String module,
                                               @RequestParam("ratingAvg") long ratingAvg,
                                               @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            // Get the existing examen
            Optional<Examen> existingExamenOpt = examenService.getExamenById(id);
            if (existingExamenOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Examen existingExamen = existingExamenOpt.get();

            // Update examen properties
            existingExamen.setTitre(titre);
            existingExamen.setDescription(description);
            existingExamen.setNiveau(niveau);
            existingExamen.setModule(module);
            existingExamen.setRatingAvg(ratingAvg);

            // Update the examen in database and handle file if provided
            Examen updatedExamen = file != null ?
                    examenService.updateExamenAvecPdf(existingExamen, file) :
                    examenService.updateExamenSansPdf(existingExamen);

            return ResponseEntity.ok(updatedExamen);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // Télécharger un PDF par ID
    @GetMapping("/fichier/{id}")
    public ResponseEntity<byte[]> getPdf(@PathVariable String id) {
        return examenService.getPdf(id);
    }

    // Supprimer un examen par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExamen(@PathVariable String id) {
        examenService.deleteExamen(id);
        return ResponseEntity.noContent().build();
    }

    // Récupérer les examens par niveau
    @GetMapping("/niveau/{niveau}")
    public ResponseEntity<List<Examen>> getExamensByNiveau(@PathVariable String niveau) {
        return ResponseEntity.ok(examenService.getExamensByNiveau(niveau));
    }

    // Récupérer les examens par module
    @GetMapping("/module/{module}")
    public ResponseEntity<List<Examen>> getExamensByModule(@PathVariable String module) {
        return ResponseEntity.ok(examenService.getExamensByModule(module));
    }

    // Vérifier si un fichier existe
    @GetMapping("/fichier/verify/{id}")
    public ResponseEntity<String> verifyFile(@PathVariable String id) {
        try {
            boolean exists = examenService.verifyFileExists(id);
            return exists ? ResponseEntity.ok("Fichier existe") : ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("ID invalide");
        }
    }
}