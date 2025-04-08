package com.example.Gii.controller;

import com.example.Gii.entity.ChefDepart;
import com.example.Gii.service.ChefDepartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chef")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ChefDepartController {
    private final ChefDepartService chefDepartService;

    // Récupérer tous les chefs de département
    @GetMapping("/AllChefDeparts")
    public ResponseEntity<List<ChefDepart>> getAllChefDeparts() {
        return ResponseEntity.ok(chefDepartService.getAllChefsDepart());
    }

    // Ajouter un chef de département
    @PostMapping("/addChefDepart")
    public ResponseEntity<ChefDepart> addChefDepart(@RequestBody ChefDepart chefDepart) {
        ChefDepart newChef = chefDepartService.addChefDepart(chefDepart);
        return ResponseEntity.ok(newChef);
    }

    // Mettre à jour un chef de département existant
    @PutMapping("/{id}")
    public ResponseEntity<ChefDepart> updateChefDepart(@PathVariable String id, @RequestBody ChefDepart chefDepartDetails) {
        System.out.println("user id " + id);
        System.out.println("data to change  " + chefDepartDetails);
        ChefDepart updatedChef = chefDepartService.updateChefDepart(id, chefDepartDetails);
        return ResponseEntity.ok(updatedChef);
    }

    // Récupérer un chef de département par ID
    @GetMapping("/{id}")
    public ResponseEntity<ChefDepart> getChefDepartById(@PathVariable String id) {
        ChefDepart chefDepart = chefDepartService.getChefDepartById(id);
        return ResponseEntity.ok(chefDepart);
    }

    // Supprimer un chef de département par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChefDepart(@PathVariable String id) {
        chefDepartService.deleteChefDepart(id);
        return ResponseEntity.noContent().build();
    }

    // Compter le nombre total de chefs de département
    @GetMapping("/count")
    public ResponseEntity<Long> countChefDepart() {
        return ResponseEntity.ok(chefDepartService.countChefsDepart());
    }
}