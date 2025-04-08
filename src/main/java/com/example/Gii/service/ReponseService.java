package com.example.Gii.service;

import com.example.Gii.entity.Reponse;
import com.example.Gii.repository.ReponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReponseService {

    private final ReponseRepository reponseRepository;

    // Récupérer toutes les réponses
    public List<Reponse> getAllReponses() {
        return reponseRepository.findAll();
    }

    // Ajouter une réponse
    public Reponse addReponse(Reponse reponse) {
        return reponseRepository.save(reponse);
    }

    // Mettre à jour une réponse existante
    public Reponse updateReponse(String id, Reponse reponseDetails) {
        Optional<Reponse> optionalReponse = reponseRepository.findById(id);
        if (optionalReponse.isPresent()) {
            Reponse reponse = optionalReponse.get();
            reponse.setContenu(reponseDetails.getContenu());
            reponse.setDateCreation(reponseDetails.getDateCreation());
            return reponseRepository.save(reponse);
        } else {
            throw new RuntimeException("Réponse non trouvée avec l'ID : " + id);
        }
    }

    // Récupérer une réponse par ID
    public Reponse getReponseById(String id) {
        return reponseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réponse non trouvée avec l'ID : " + id));
    }

    // Supprimer une réponse par ID
    public void deleteReponse(String id) {
        reponseRepository.deleteById(id);
    }

    // Compter le nombre total de réponses
    public long countReponses() {
        return reponseRepository.count();
    }
}
