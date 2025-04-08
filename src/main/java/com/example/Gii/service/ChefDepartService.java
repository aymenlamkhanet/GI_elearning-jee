package com.example.Gii.service;

import com.example.Gii.entity.ChefDepart;
import com.example.Gii.repository.ChefDepartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChefDepartService {
    private final ChefDepartRepository chefDepartRepository;

    // Retourner tous les chefs de département
    public List<ChefDepart> getAllChefsDepart() {
        return chefDepartRepository.findAll();
    }

    // Retourner un chef de département par ID
    public ChefDepart getChefDepartById(String id) {
        return chefDepartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chef de département non trouvé avec l'ID : " + id));
    }

    // Ajouter un chef de département
    public ChefDepart addChefDepart(ChefDepart chefDepart) {

        return chefDepartRepository.save(chefDepart);
    }

    // Modifier un chef de département
    public ChefDepart updateChefDepart(String id, ChefDepart chefDepartDetails) {
        ChefDepart chefDepart = chefDepartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chef de département non trouvé avec l'ID : " + id));

        chefDepart.setPrenom(chefDepartDetails.getPrenom());
        chefDepart.setNom(chefDepartDetails.getNom());
        chefDepart.setEmail(chefDepartDetails.getEmail());
        chefDepart.setMotDePasse(chefDepartDetails.getMotDePasse());
        chefDepart.setDepartement(chefDepartDetails.getDepartement());


        return chefDepartRepository.save(chefDepart);
    }

    // Supprimer un chef de département par ID
    public void deleteChefDepart(String id) {
        if (!chefDepartRepository.existsById(id)) {
            throw new RuntimeException("Chef de département non trouvé avec l'ID : " + id);
        }
        chefDepartRepository.deleteById(id);
    }

    // Compter le nombre de chefs de département
    public Long countChefsDepart() {
        return chefDepartRepository.count();
    }
}
