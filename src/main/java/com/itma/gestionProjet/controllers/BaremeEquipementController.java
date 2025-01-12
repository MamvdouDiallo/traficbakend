package com.itma.gestionProjet.controllers;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.dtos.BaremeEquipementDTO;
import com.itma.gestionProjet.requests.BaremeEquipementRequest;
import com.itma.gestionProjet.services.BaremeEquipementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/baremeEquipement")
public class BaremeEquipementController {

    @Autowired
    private BaremeEquipementService baremeEquipementService;

    @PostMapping
    public ResponseEntity<AApiResponse<BaremeEquipementDTO>> createBaremeEquipements(@RequestBody List<BaremeEquipementRequest> requests) {
        try {
            List<BaremeEquipementDTO> baremeEquipementDTOs = baremeEquipementService.createBaremeEquipements(requests);
            return ResponseEntity.ok(new AApiResponse<>(200, baremeEquipementDTOs, 0, baremeEquipementDTOs.size(), "Equipements ajoutés avec succès", baremeEquipementDTOs.size()));
        } catch (Exception e) {
            return ResponseEntity.ok(new AApiResponse<>(500, null, 0, 0, "Erreur lors de l'ajout des équipements", 0));
        }
    }

    @GetMapping
    public ResponseEntity<AApiResponse<BaremeEquipementDTO>> getAllBaremeEquipements(Pageable pageable) {
        try {
            Page<BaremeEquipementDTO> baremeEquipementDTOs = baremeEquipementService.getAllBaremeEquipements(pageable);
            return ResponseEntity.ok(new AApiResponse<>(200, baremeEquipementDTOs.getContent(), pageable.getPageNumber(), pageable.getPageSize(), "Liste des équipements", baremeEquipementDTOs.getTotalElements()));
        } catch (Exception e) {
            return ResponseEntity.ok(new AApiResponse<>(500, null, 0, 0, "Erreur lors de la récupération des équipements", 0));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AApiResponse<BaremeEquipementDTO>> getBaremeEquipementById(@PathVariable Long id) {
        try {
            BaremeEquipementDTO baremeEquipementDTO = baremeEquipementService.getBaremeEquipementById(id);
            return ResponseEntity.ok(new AApiResponse<>(200, List.of(baremeEquipementDTO), 0, 1, "Equipement trouvé", 1));
        } catch (Exception e) {
            return ResponseEntity.ok(new AApiResponse<>(500, null, 0, 0, "Erreur lors de la récupération de l'équipement", 0));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AApiResponse<BaremeEquipementDTO>> updateBaremeEquipement(@PathVariable Long id, @RequestBody BaremeEquipementRequest request) {
        try {
            BaremeEquipementDTO updatedDTO = baremeEquipementService.updateBaremeEquipement(id, request);
            return ResponseEntity.ok(new AApiResponse<>(200, List.of(updatedDTO), 0, 1, "Equipement mis à jour avec succès", 1));
        } catch (Exception e) {
            return ResponseEntity.ok(new AApiResponse<>(500, null, 0, 0, "Erreur lors de la mise à jour de l'équipement", 0));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AApiResponse<Void>> deleteBaremeEquipement(@PathVariable Long id) {
        try {
            baremeEquipementService.deleteBaremeEquipement(id);
            return ResponseEntity.ok(new AApiResponse<>(200, null, 0, 0, "Equipement supprimé avec succès", 0));
        } catch (Exception e) {
            return ResponseEntity.ok(new AApiResponse<>(500, null, 0, 0, "Erreur lors de la suppression de l'équipement", 0));
        }
    }
}
