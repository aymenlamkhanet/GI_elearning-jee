package com.example.Gii.service;

import com.example.Gii.entity.Ouvrage;
import com.example.Gii.repository.OuvrageRepository;
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
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OuvrageService {
    private final OuvrageRepository ouvrageRepository;
    private final GridFSBucket gridFSBucket;


    public String uploadPdf(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            GridFSUploadOptions options = new GridFSUploadOptions().chunkSizeBytes(1024);
            ObjectId fileId = gridFSBucket.uploadFromStream(file.getOriginalFilename(), inputStream, options);
            return fileId.toHexString();
        }
    }

    public List<String> getAllDistinctModules() {
        return ouvrageRepository.findDistinctModules();
    }

    public Long countOuvrages() {
        return ouvrageRepository.count();
    }

    public Map<String, Object> getOuvrageStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalOuvrages", countOuvrages());
        return stats;
    }

    public List<Ouvrage> getRecentOuvrages() {
        return ouvrageRepository.findTop5ByOrderByIdDesc();
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

    public void updatePdf(Ouvrage ouvrage, MultipartFile file) throws IOException {
        // Delete old file if it exists
        if (ouvrage.getFichierId() != null) {
            supprimerPdf(ouvrage.getFichierId());
        }

        // Upload new file
        String newFileId = uploadPdf(file);
        ouvrage.setFichierId(newFileId);
    }

    public Ouvrage updateOuvrage(Ouvrage ouvrage) {
        return ouvrageRepository.save(ouvrage);
    }



    public void supprimerPdf(String fileId) {
        if (fileId == null || !ObjectId.isValid(fileId)) {
            // Log an error message for invalid fileId
            System.err.println("Invalid fileId provided: " + fileId);
            return; // Exit the method to avoid IllegalArgumentException
        }
        gridFSBucket.delete(new ObjectId(fileId));
    }

    public void deleteOuvrage(String id) {
        Optional<Ouvrage> ouvrageOpt = ouvrageRepository.findById(id);
        ouvrageOpt.ifPresent(ouvrage -> {
            if (ouvrage.getFichierId() != null) {
                supprimerPdf(ouvrage.getFichierId());
            }
            ouvrageRepository.deleteById(id);
        });
    }

    public List<Ouvrage> getAllOuvrages() {
        return ouvrageRepository.findAll();
    }

    public Ouvrage ajouterOuvrageAvecPdf(Ouvrage ouvrage, MultipartFile file) throws IOException {
        String fileId = uploadPdf(file);
        try {
            gridFSBucket.downloadToStream(new ObjectId(fileId), new ByteArrayOutputStream());
        } catch (Exception e) {
            gridFSBucket.delete(new ObjectId(fileId));
            throw new IOException("Échec de vérification GridFS", e);
        }

        ouvrage.setFichierId(fileId);
        return ouvrageRepository.save(ouvrage);
    }

    public Optional<Ouvrage> getOuvrageById(String id) {
        return ouvrageRepository.findById(id);
    }

    public List<Ouvrage> getOuvragesByNiveau(String niveau) {
        return ouvrageRepository.findByNiveau(niveau);
    }

    public List<Ouvrage> getOuvragesByModule(String module) {
        return ouvrageRepository.findByModule(module);
    }

    public boolean verifyFileExists(String fileId) {
        try {
            return gridFSBucket.find().first() != null;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ID de fichier invalide: " + fileId);
        }
    }
}