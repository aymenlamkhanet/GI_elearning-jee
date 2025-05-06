package com.example.Gii.service;

import com.example.Gii.entity.Examen;
import com.example.Gii.repository.ExamenRepository;
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
import java.util.Map;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class ExamenService {
    private final ExamenRepository examenRepository;
    private final GridFSBucket gridFSBucket;
    private final String uploadDir = "uploads";

    public String uploadPdf(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            GridFSUploadOptions options = new GridFSUploadOptions().chunkSizeBytes(1024);
            ObjectId fileId = gridFSBucket.uploadFromStream(file.getOriginalFilename(), inputStream, options);
            return fileId.toHexString();
        }
    }

    public Long countExamens() {
        return examenRepository.count();
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

    public List<Examen> getAllExamens() {
        return examenRepository.findAll();
    }

    public Optional<Examen> getExamenById(String id) {
        return examenRepository.findById(id);
    }

    public List<Examen> getExamensByNiveau(String niveau) {
        return examenRepository.findByNiveau(niveau);
    }

    public List<Examen> getExamensByModule(String module) {
        return examenRepository.findByModule(module);
    }

    public void deleteExamen(String id) {
        Optional<Examen> examenOpt = examenRepository.findById(id);
        examenOpt.ifPresent(examen -> {
            if (examen.getFichierId() != null) {
                supprimerPdf(examen.getFichierId());
            }
            examenRepository.deleteById(id);
        });
    }

    public Examen ajouterExamenAvecPdf(Examen examen, MultipartFile file) throws IOException {
        String fileId = uploadPdf(file);

        try {
            gridFSBucket.downloadToStream(new ObjectId(fileId), new ByteArrayOutputStream());
        } catch (Exception e) {
            gridFSBucket.delete(new ObjectId(fileId));
            throw new IOException("Échec de vérification GridFS", e);
        }

        examen.setFichierId(fileId);
        examen.setDate(LocalDateTime.now()); // Date d'ajout

        return examenRepository.save(examen);
    }

    public Examen updateExamenAvecPdf(Examen examen, MultipartFile file) throws IOException {
        // First, save the updated examen data
        Examen savedExamen = examenRepository.save(examen);

        // Then, handle the file update
        String fileName = savedExamen.getId() + ".pdf";
        Path filePath = Paths.get(uploadDir, fileName);

        // Create directory if it doesn't exist
        Files.createDirectories(filePath.getParent());

        // Delete existing file if it exists
        Files.deleteIfExists(filePath);

        // Save the new file
        Files.copy(file.getInputStream(), filePath);

        return savedExamen;
    }

    public Examen updateExamenSansPdf(Examen examen) {
        return examenRepository.save(examen);
    }

    public boolean verifyFileExists(String fileId) {
        try {
            return gridFSBucket.find().first() != null;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ID de fichier invalide: " + fileId);
        }
    }

    public List<Examen> getRecentExams() {
        return examenRepository.findTop5ByOrderByIdDesc();
    }

    // New method: Get exam statistics
    public Map<String, Object> getExamStatistics() {
        Long totalExams = examenRepository.count();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalExams", totalExams);

        // You can expand this later (e.g., by date, status, etc.)
        return stats;
    }
}