package com.example.Gii.controller;
import com.example.Gii.entity.Cours;
import com.example.Gii.service.CoursService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/cours")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CoursController {
    private final CoursService coursService;

    // ✅ 1. Récupérer tous les cours
    @GetMapping
    public ResponseEntity<List<Cours>> getAllCours() {
        return ResponseEntity.ok(coursService.getAllCours());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Cours> updateCours(@PathVariable String id,
                                             @RequestParam("titre") String titre,
                                             @RequestParam("description") String description,
                                             @RequestParam("niveau") String niveau,
                                             @RequestParam("module") String module,
                                             @RequestParam("duree") String duree,
                                             @RequestParam(value = "liens", required = false) String liens,
                                             @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            // Get the existing course
            Optional<Cours> existingCoursOpt = coursService.getCoursById(id);
            if (existingCoursOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Cours existingCours = existingCoursOpt.get();

            // Update course properties
            existingCours.setTitre(titre);
            existingCours.setDescription(description);
            existingCours.setNiveau(niveau);
            existingCours.setModule(module);
            existingCours.setDuree(duree);
            existingCours.setLiens(liens != null ? liens : new String());


            // Update the course in database and handle file if provided
            Cours updatedCours = file != null ?
                    coursService.updateCoursAvecPdf(existingCours, file) :
                    coursService.updateCoursSansPdf(existingCours);

            return ResponseEntity.ok(updatedCours);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // ✅ 2. Récupérer un cours par ID
    @GetMapping("/{id}")
    public ResponseEntity<Cours> getCoursById(@PathVariable String id) {
        Optional<Cours> cours = coursService.getCoursById(id);
        return cours.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // ✅ 3. Ajouter un cours avec fichier PDF
    @PostMapping("/ajouter")
    public ResponseEntity<Cours> ajouterCours(@RequestParam("titre") String titre,
                                              @RequestParam("description") String description,
                                              @RequestParam("niveau") String niveau,
                                              @RequestParam("module") String module,
                                              @RequestParam("duree") String duree,
                                              @RequestParam("ratingAvg") Long ratingAvg,
                                              @RequestParam("date") LocalDateTime date,
                                              @RequestParam(value = "liens", required = false) String liens,
                                              @RequestParam("file") MultipartFile file) {
        try {
            Cours cours = new Cours();
            cours.setTitre(titre);
            cours.setDescription(description);
            cours.setNiveau(niveau);
            cours.setModule(module);
            cours.setDuree(duree);
            cours.setLiens(liens != null ? liens : new String());
            cours.setRatingAvg(ratingAvg);
            cours.setDate(date);

            Cours savedCours = coursService.ajouterCoursAvecPdf(cours, file);
            return ResponseEntity.ok(savedCours);
        } catch (IOException e) {
            e.printStackTrace(); // Add this to see the full stack trace in logs
            return ResponseEntity.internalServerError().build();
        }
    }



    // ✅ 4. Télécharger un PDF par ID
    @GetMapping("/fichier/{id}")
    public ResponseEntity<byte[]> getPdf(@PathVariable String id) {
        return coursService.getPdf(id);
    }



    // ✅ 5. Supprimer un cours par ID (et supprimer le fichier)
    @DeleteMapping("/{id}")  // Make sure this matches your fetch URL pattern
    public ResponseEntity<Void> deleteFichier(@PathVariable String id) {
        coursService.deleteCours(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ 6. Récupérer les cours par niveau
    @GetMapping("/niveau/{niveau}")
    public ResponseEntity<List<Cours>> getCoursByNiveau(@PathVariable String niveau) {
        return ResponseEntity.ok(coursService.getCoursByNiveau(niveau));
    }

    // ✅ 7. Récupérer les cours par module
    @GetMapping("/module/{module}")
    public ResponseEntity<List<Cours>> getCoursByModule(@PathVariable String module) {
        return ResponseEntity.ok(coursService.getCoursByModule(module));
    }

    @GetMapping("/fichier/verify/{id}")
    public ResponseEntity<String> verifyFile(@PathVariable String id) {
        try {
            boolean exists = coursService.verifyFileExists(id);
            return exists ? ResponseEntity.ok("Fichier existe") : ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("ID invalide");
        }
    }
}
