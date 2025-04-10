package com.example.Gii.controller;

import com.example.Gii.entity.Exercice;
import com.example.Gii.service.ExerciceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/exercices")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ExerciceController {
    private final ExerciceService exerciceService;

    // Récupérer tous les exercices
    @GetMapping
    public ResponseEntity<List<Exercice>> getAllExercices() {
        return ResponseEntity.ok(exerciceService.getAllExercices());
    }

    // Récupérer un exercice par ID
    @GetMapping("/{id}")
    public ResponseEntity<Exercice> getExerciceById(@PathVariable String id) {
        Optional<Exercice> exercice = exerciceService.getExerciceById(id);
        return exercice.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Ajouter un exercice avec fichier PDF
    @PostMapping("/ajouter")
    public ResponseEntity<Exercice> ajouterExercice(@RequestParam("titre") String titre,
                                                    @RequestParam("description") String description,
                                                    @RequestParam("niveau") String niveau,
                                                    @RequestParam("module") String module,
                                                    @RequestParam("ratingAvg") long ratingAvg, // Change from Long to long
                                                    @RequestParam(value = "date", required = false) LocalDateTime date,
                                                    @RequestParam("file") MultipartFile file) {
        try {
            Exercice exercice = new Exercice();
            exercice.setTitre(titre);
            exercice.setDescription(description);
            exercice.setNiveau(niveau);
            exercice.setModule(module);
            exercice.setRatingAvg(ratingAvg); // Set the rating from the request
            exercice.setDate(date != null ? date : LocalDateTime.now()); // Use provided date or current time

            Exercice savedExercice = exerciceService.ajouterExerciceAvecPdf(exercice, file);
            return ResponseEntity.ok(savedExercice);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // Mettre à jour un exercice
    @PutMapping("/update/{id}")
    public ResponseEntity<Exercice> updateExercice(@PathVariable String id,
                                                   @RequestParam("titre") String titre,
                                                   @RequestParam("description") String description,
                                                   @RequestParam("niveau") String niveau,
                                                   @RequestParam("module") String module,
                                                   @RequestParam("ratingAvg") long ratingAvg,
                                                   @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            // Get the existing exercice
            Optional<Exercice> existingExerciceOpt = exerciceService.getExerciceById(id);
            if (existingExerciceOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Exercice existingExercice = existingExerciceOpt.get();

            // Update exercice properties
            existingExercice.setTitre(titre);
            existingExercice.setDescription(description);
            existingExercice.setNiveau(niveau);
            existingExercice.setModule(module);
            existingExercice.setRatingAvg(ratingAvg);

            // Update the exercice in database and handle file if provided
            Exercice updatedExercice = file != null ?
                    exerciceService.updateExerciceAvecPdf(existingExercice, file) :
                    exerciceService.updateExerciceSansPdf(existingExercice);

            return ResponseEntity.ok(updatedExercice);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // Télécharger un PDF par ID
    @GetMapping("/fichier/{id}")
    public ResponseEntity<byte[]> getPdf(@PathVariable String id) {
        return exerciceService.getPdf(id);
    }

    // Supprimer un exercice par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercice(@PathVariable String id) {
        exerciceService.deleteExercice(id);
        return ResponseEntity.noContent().build();
    }

    // Récupérer les exercices par niveau
    @GetMapping("/niveau/{niveau}")
    public ResponseEntity<List<Exercice>> getExercicesByNiveau(@PathVariable String niveau) {
        return ResponseEntity.ok(exerciceService.getExercicesByNiveau(niveau));
    }

    // Récupérer les exercices par module
    @GetMapping("/module/{module}")
    public ResponseEntity<List<Exercice>> getExercicesByModule(@PathVariable String module) {
        return ResponseEntity.ok(exerciceService.getExercicesByModule(module));
    }

    // Vérifier si un fichier existe
    @GetMapping("/fichier/verify/{id}")
    public ResponseEntity<String> verifyFile(@PathVariable String id) {
        try {
            boolean exists = exerciceService.verifyFileExists(id);
            return exists ? ResponseEntity.ok("Fichier existe") : ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("ID invalide");
        }
    }
}