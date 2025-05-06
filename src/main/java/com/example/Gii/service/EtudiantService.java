package com.example.Gii.service;

import com.example.Gii.entity.Etudiant;
import com.example.Gii.repository.EtudiantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EtudiantService {
    private final   EtudiantRepository etudiantRepository;


    public List<Etudiant> findByNiveau(String niveau) {
        return etudiantRepository.findByNiveau(niveau);
    }


    public Long countEtudiants() {
        return etudiantRepository.count();
    }

    // retourner tous les etudiants
    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }


    public Etudiant getEtudiantById(String id) {
        return (Etudiant) etudiantRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));
    }

    //Ajouter Etudiant
    public Etudiant AddEtudiant(Etudiant etudiant) {
        if (etudiant.getNiveau() == null) {
            throw new IllegalArgumentException("Le niveau est obligatoire");
        }
        return etudiantRepository.save(etudiant);
    }

    public List<Etudiant> getRecentStudents() {
        return etudiantRepository.findTop5ByOrderByIdDesc(); // fallback if no createdAt
    }

    // Basic stats (can be extended later)
    public Map<String, Object> getStudentStatistics() {
        Long totalStudents = etudiantRepository.count();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalStudents", totalStudents);

        // You can add gender distribution or other stats here later
        return stats;
    }


    //modifier Etudiant
    public Etudiant updateUEtudiant(String id, Etudiant EtudiantDetails) {
        Etudiant etudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + id));

        etudiant.setPrenom(EtudiantDetails.getPrenom());
        etudiant.setNom(EtudiantDetails.getNom());
        etudiant.setEmail(EtudiantDetails.getEmail());
        etudiant.setPhone(EtudiantDetails.getPhone());
        etudiant.setNiveau(EtudiantDetails.getNiveau());
        etudiant.setMotDePasse(EtudiantDetails.getMotDePasse());


        return etudiantRepository.save(etudiant);
    }




    // Supprimer etudiant par ID
    public void deleteEtudiant(String id) {
        if (!etudiantRepository.existsById(id)) {
            throw new RuntimeException("Etudiant non trouvé avec l'ID : " + id);
        }
        etudiantRepository.deleteById(id);
    }




}