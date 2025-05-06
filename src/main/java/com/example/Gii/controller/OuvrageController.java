package com.example.Gii.controller;

import com.example.Gii.entity.Ouvrage;
import com.example.Gii.service.OuvrageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ouvrages")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OuvrageController {
    private final OuvrageService ouvrageService;

    @GetMapping("/modules/distinct")
    public ResponseEntity<List<String>> getDistinctModules() {
        return ResponseEntity.ok(ouvrageService.getAllDistinctModules());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countOuvrages() {
        return ResponseEntity.ok(ouvrageService.countOuvrages());
    }

    // 3. Get stats
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getOuvrageStats() {
        return ResponseEntity.ok(ouvrageService.getOuvrageStatistics());
    }

    // 4. Get recent ouvrages
    @GetMapping("/recent")
    public ResponseEntity<List<Ouvrage>> getRecentOuvrages() {
        return ResponseEntity.ok(ouvrageService.getRecentOuvrages());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Ouvrage> updateOuvrage(
            @PathVariable String id,
            @RequestParam(value = "titre", required = false) String titre,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "niveau", required = false) String niveau,
            @RequestParam(value = "module", required = false) String module,
            @RequestParam(value = "version", required = false) Long version,
            @RequestParam(value = "datePublication", required = false) String datePublication,
            @RequestParam(value = "ratingAvg", required = false) Long ratingAvg,
            @RequestParam(value = "date", required = false) LocalDateTime date,
            @RequestParam(value = "nbrPages", required = false) Long nbrPages,
            @RequestParam(value = "domaine", required = false) String domaine,
            @RequestParam(value = "reviews", required = false) String reviews,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        try {
            Ouvrage existingOuvrage = ouvrageService.getOuvrageById(id)
                    .orElseThrow(() -> new RuntimeException("Ouvrage not found"));

            // Update only the provided fields
            if (titre != null) existingOuvrage.setTitre(titre);
            if (description != null) existingOuvrage.setDescription(description);
            if (niveau != null) existingOuvrage.setNiveau(niveau);
            if (module != null) existingOuvrage.setModule(module);
            if (version != null) existingOuvrage.setVersion(version);
            if (datePublication != null) existingOuvrage.setDatePublication(datePublication);
            if (nbrPages != null) existingOuvrage.setNbrPages(nbrPages);
            if (domaine != null) existingOuvrage.setDomaine(domaine);
            if (reviews != null) existingOuvrage.setReviews(reviews);

            // Handle file update if provided
            if (file != null && !file.isEmpty()) {
                ouvrageService.updatePdf(existingOuvrage, file);
            }

            Ouvrage updatedOuvrage = ouvrageService.updateOuvrage(existingOuvrage);
            return ResponseEntity.ok(updatedOuvrage);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping
    public ResponseEntity<List<Ouvrage>> getAllOuvrages() {
        return ResponseEntity.ok(ouvrageService.getAllOuvrages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ouvrage> getOuvrageById(@PathVariable String id) {
        Optional<Ouvrage> ouvrage = ouvrageService.getOuvrageById(id);
        return ouvrage.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/ajouter")
    public ResponseEntity<Ouvrage> ajouterOuvrage(@RequestParam("titre") String titre,
                                                  @RequestParam("description") String description,
                                                  @RequestParam("niveau") String niveau,
                                                  @RequestParam("module") String module,
                                                  @RequestParam("version") Long version,
                                                  @RequestParam("datePublication") String datePublication,
                                                  @RequestParam("ratingAvg") Long ratingAvg,
                                                  @RequestParam("date") LocalDateTime date,
                                                  @RequestParam("nbrPages") Long nbrPages,
                                                  @RequestParam("domaine") String domaine,
                                                  @RequestParam("reviews") String reviews,
                                                  @RequestParam("file") MultipartFile file) {
        try {
            Ouvrage ouvrage = new Ouvrage();
            ouvrage.setTitre(titre);
            ouvrage.setDescription(description);
            ouvrage.setNiveau(niveau);
            ouvrage.setModule(module);
            ouvrage.setVersion(version);
            ouvrage.setDatePublication(datePublication);
            ouvrage.setNbrPages(nbrPages);
            ouvrage.setDomaine(domaine);
            ouvrage.setReviews(reviews);

            Ouvrage savedOuvrage = ouvrageService.ajouterOuvrageAvecPdf(ouvrage, file);
            return ResponseEntity.ok(savedOuvrage);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/fichier/{id}")
    public ResponseEntity<byte[]> getPdf(@PathVariable String id) {
        return ouvrageService.getPdf(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOuvrage(@PathVariable String id) {
        ouvrageService.deleteOuvrage(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/niveau/{niveau}")
    public ResponseEntity<List<Ouvrage>> getOuvragesByNiveau(@PathVariable String niveau) {
        return ResponseEntity.ok(ouvrageService.getOuvragesByNiveau(niveau));
    }

    @GetMapping("/module/{module}")
    public ResponseEntity<List<Ouvrage>> getOuvragesByModule(@PathVariable String module) {
        return ResponseEntity.ok(ouvrageService.getOuvragesByModule(module));
    }

    @GetMapping("/fichier/verify/{id}")
    public ResponseEntity<String> verifyFile(@PathVariable String id) {
        try {
            boolean exists = ouvrageService.verifyFileExists(id);
            return exists ? ResponseEntity.ok("Fichier existe") : ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("ID invalide");
        }
    }
}