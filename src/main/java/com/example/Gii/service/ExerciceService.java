package com.example.Gii.service;

import com.example.Gii.entity.Exercice;
import com.example.Gii.repository.ExerciceRepository;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExerciceService {
    private final ExerciceRepository exerciceRepository;
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

    public List<Exercice> getAllExercices() {
        return exerciceRepository.findAll();
    }

    public Optional<Exercice> getExerciceById(String id) {
        return exerciceRepository.findById(id);
    }

    public List<Exercice> getExercicesByNiveau(String niveau) {
        return exerciceRepository.findByNiveau(niveau);
    }

    public List<Exercice> getExercicesByModule(String module) {
        return exerciceRepository.findByModule(module);
    }

    public void deleteExercice(String id) {
        Optional<Exercice> exerciceOpt = exerciceRepository.findById(id);
        exerciceOpt.ifPresent(exercice -> {
            if (exercice.getFichierId() != null) {
                supprimerPdf(exercice.getFichierId());
            }
            exerciceRepository.deleteById(id);
        });
    }

    public Exercice ajouterExerciceAvecPdf(Exercice exercice, MultipartFile file) throws IOException {
        String fileId = uploadPdf(file);

        try {
            gridFSBucket.downloadToStream(new ObjectId(fileId), new ByteArrayOutputStream());
        } catch (Exception e) {
            gridFSBucket.delete(new ObjectId(fileId));
            throw new IOException("Échec de vérification GridFS", e);
        }

        exercice.setFichierId(fileId);
        exercice.setRatingAvg(0); // Valeur initiale
        exercice.setDate(LocalDateTime.now()); // Date d'ajout

        return exerciceRepository.save(exercice);
    }

    public Exercice updateExerciceAvecPdf(Exercice exercice, MultipartFile file) throws IOException {
        // First, save the updated exercice data
        Exercice savedExercice = exerciceRepository.save(exercice);

        // Then, handle the file update
        String fileName = savedExercice.getId() + ".pdf";
        Path filePath = Paths.get(uploadDir, fileName);

        // Create directory if it doesn't exist
        Files.createDirectories(filePath.getParent());

        // Delete existing file if it exists
        Files.deleteIfExists(filePath);

        // Save the new file
        Files.copy(file.getInputStream(), filePath);

        return savedExercice;
    }

    public Exercice updateExerciceSansPdf(Exercice exercice) {
        return exerciceRepository.save(exercice);
    }

    public boolean verifyFileExists(String fileId) {
        try {
            return gridFSBucket.find().first() != null;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ID de fichier invalide: " + fileId);
        }
    }
}