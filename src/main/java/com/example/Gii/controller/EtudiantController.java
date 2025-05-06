package com.example.Gii.controller;


import com.example.Gii.entity.Etudiant;
import com.example.Gii.service.EtudiantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/etudiant")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EtudiantController {
    private final EtudiantService etudiantService;

    @GetMapping("/AllEtudiants")
    public ResponseEntity<List<Etudiant>> getAllEtudiants() {
        return ResponseEntity.ok(etudiantService.getAllEtudiants());
    }

    @PostMapping("/addEtudiant")
    public ResponseEntity<Etudiant> addEtudiant(@RequestBody Etudiant etudiant) {
        return ResponseEntity.ok(etudiantService.AddEtudiant(etudiant));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Etudiant> updateEtudiant(
            @PathVariable String id,
            @RequestBody Etudiant etudiantDetails) {
        return ResponseEntity.ok(etudiantService.updateUEtudiant(id, etudiantDetails));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Etudiant> getEtudiantById(@PathVariable String id) {
        return ResponseEntity.ok(etudiantService.getEtudiantById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEtudiant(@PathVariable String id) {
        etudiantService.deleteEtudiant(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/recent")
    public ResponseEntity<List<Etudiant>> getRecentStudents() {
        return ResponseEntity.ok(etudiantService.getRecentStudents());
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStudentStats() {
        return ResponseEntity.ok(etudiantService.getStudentStatistics());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countEtudiant() {
        return ResponseEntity.ok(etudiantService.countEtudiants());
    }



}
