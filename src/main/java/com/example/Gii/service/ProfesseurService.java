package com.example.Gii.service;

import com.example.Gii.entity.Professeur;
import com.example.Gii.repository.ProfesseurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class ProfesseurService {
    private final ProfesseurRepository professeurRepository;

    // Retourner tous les professeurs
    public List<Professeur> getAllProfesseurs() {
        return professeurRepository.findAll();
    }

    // Retourner un professeur par ID
    public Professeur getProfesseurById(String id) {
        return professeurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professeur non trouvé avec l'ID : " + id));
    }

    // Ajouter un professeur
    public Professeur addProfesseur(Professeur professeur) {
        if (professeur.getModule() == null) {
            throw new IllegalArgumentException("La spécialité est obligatoire");
        }
        return professeurRepository.save(professeur);
    }

    // Modifier un professeur
    public Professeur updateProfesseur(String id, Professeur professeurDetails) {
        Professeur professeur = professeurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professeur non trouvé avec l'ID : " + id));

        professeur.setPrenom(professeurDetails.getPrenom());
        professeur.setNom(professeurDetails.getNom());
        professeur.setEmail(professeurDetails.getEmail());
        professeur.setPhone(professeurDetails.getPhone());
        professeur.setModule(professeurDetails.getModule());
        professeur.setMotDePasse(professeurDetails.getMotDePasse());

        return professeurRepository.save(professeur);
    }

    // Supprimer un professeur par ID
    public void deleteProfesseur(String id) {
        if (!professeurRepository.existsById(id)) {
            throw new RuntimeException("Professeur non trouvé avec l'ID : " + id);
        }
        professeurRepository.deleteById(id);
    }

    // Compter le nombre de professeurs
    public Long countProfesseurs() {
        return professeurRepository.count();
    }

    public List<Professeur> getRecentProfessors() {
        return professeurRepository.findTop5ByOrderByIdDesc();
    }

    // Basic stats (can be extended later)
    public Map<String, Object> getProfessorStatistics() {
        Long totalProfessors = countProfesseurs();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalProfessors", totalProfessors);

        // You can add department distribution or other stats here later
        return stats;
    }
}
