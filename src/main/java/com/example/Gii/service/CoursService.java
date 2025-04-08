package com.example.Gii.service;

import com.example.Gii.entity.Cours;
import com.example.Gii.repository.CoursRepository;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CoursService {
    private final CoursRepository coursRepository;
    private final GridFSBucket gridFSBucket;
    private final String uploadDir = "uploads";




    public String uploadPdf(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            GridFSUploadOptions options = new GridFSUploadOptions().chunkSizeBytes(1024);
            ObjectId fileId = gridFSBucket.uploadFromStream(file.getOriginalFilename(), inputStream, options);
            return fileId.toHexString();
        }
    }



    public ResponseEntity<byte[]> getPdf(String id) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            gridFSBucket.downloadToStream(new ObjectId(id), outputStream);
            byte[] data = outputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/pdf");
            headers.add("Content-Disposition", "attachment; filename=\"document.pdf\"");

            return new ResponseEntity<>(data, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public void supprimerPdf(String fileId) {
        gridFSBucket.delete(new ObjectId(fileId));
    }

    public void deleteCours(String id) {
        Optional<Cours> coursOpt = coursRepository.findById(id);
        coursOpt.ifPresent(cours -> {
            if (cours.getFichierId() != null) { // ✅ Corrigé ici
                supprimerPdf(cours.getFichierId());
            }
            coursRepository.deleteById(id);
        });
    }

    public List<Cours> getAllCours() {
        return coursRepository.findAll();
    }

    // Dans CoursService.java
    public Cours ajouterCoursAvecPdf(Cours cours, MultipartFile file) throws IOException {
        String fileId = uploadPdf(file);

        try {
            gridFSBucket.downloadToStream(new ObjectId(fileId), new ByteArrayOutputStream());
        } catch (Exception e) {
            gridFSBucket.delete(new ObjectId(fileId));
            throw new IOException("Échec de vérification GridFS", e);
        }

        cours.setFichierId(fileId);
        cours.setRatingAvg(0); // Valeur initiale
        cours.setDate(LocalDateTime.now()); // Date d'ajout

        return coursRepository.save(cours);
    }

    public Optional<Cours> getCoursById(String id) {
        return coursRepository.findById(id);
    }

    public List<Cours> getCoursByNiveau(String niveau) {
        return coursRepository.findByNiveau(niveau);
    }

    public List<Cours> getCoursByModule(String module) {
        return coursRepository.findByModule(module);
    }

    public boolean verifyFileExists(String fileId) {
        try {
            return gridFSBucket.find().first() != null;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ID de fichier invalide: " + fileId);
        }
    }

    // Add these methods to your CoursService class

    /**
     * Update a course with a new PDF file
     */
    public Cours updateCoursAvecPdf(Cours cours, MultipartFile file) throws IOException {
        // First, save the updated course data
        Cours savedCours = coursRepository.save(cours);

        // Then, handle the file update
        String fileName = savedCours.getId() + ".pdf";
        Path filePath = Paths.get(uploadDir, fileName);

        // Create directory if it doesn't exist
        Files.createDirectories(filePath.getParent());

        // Delete existing file if it exists
        Files.deleteIfExists(filePath);

        // Save the new file
        Files.copy(file.getInputStream(), filePath);

        return savedCours;
    }

    /**
     * Update a course without changing the PDF file
     */
    public Cours updateCoursSansPdf(Cours cours) {
        return coursRepository.save(cours);
    }
}

