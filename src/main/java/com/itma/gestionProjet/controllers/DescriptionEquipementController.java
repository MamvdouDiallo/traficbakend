package com.itma.gestionProjet.controllers;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.entities.DescriptionEquipement;
import com.itma.gestionProjet.requests.DescriptionEquipementRequest;
import com.itma.gestionProjet.services.DescriptionEquipementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/equipements")
public class DescriptionEquipementController {

    @Autowired
    private DescriptionEquipementService descriptionEquipementService;

    @PostMapping
    public ResponseEntity<AApiResponse<List<DescriptionEquipement>>> createDescriptionEquipement(@RequestBody List<DescriptionEquipementRequest> descriptionEquipementRequests) {
        AApiResponse<List<DescriptionEquipement>> response = new AApiResponse<>();
        try {
            List<DescriptionEquipement> descriptionEquipements = (List<DescriptionEquipement>) descriptionEquipementService.createDescriptionEquipement(descriptionEquipementRequests);
            response.setResponseCode(HttpStatus.CREATED.value());
            response.setData(Collections.singletonList(descriptionEquipements));
            response.setMessage("Équipements créés avec succès");
            response.setLength(descriptionEquipements.size());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Erreur lors de la création des équipements : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public AApiResponse<DescriptionEquipement> getAllEquipements(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        return descriptionEquipementService.getAllDescriptionEquipements(page, size);
    }

    @GetMapping("/{id}")
    public AApiResponse<DescriptionEquipement> getEquipementById(@PathVariable Long id) {
        return descriptionEquipementService.getDescriptionEquipementById(id);
    }

    @PutMapping("/{id}")
    public AApiResponse<DescriptionEquipement> updateEquipement(@PathVariable Long id, @RequestBody DescriptionEquipement descriptionEquipement) {
        return descriptionEquipementService.updateDescriptionEquipement(id, descriptionEquipement);
    }

    @DeleteMapping("/{id}")
    public AApiResponse<DescriptionEquipement> deleteEquipement(@PathVariable Long id) {
        return descriptionEquipementService.deleteDescriptionEquipement(id);
    }

    @GetMapping("/by-codePap")
    public ResponseEntity<AApiResponse<List<DescriptionEquipement>>> getDescriptionByCodePap(@RequestParam String codePap,
                                                                                             @RequestParam(defaultValue = "0") int offset,
                                                                                             @RequestParam(defaultValue = "100") int max) {
        AApiResponse<List<DescriptionEquipement>> response = descriptionEquipementService.getEquipementByCodePap(codePap, offset, max);

        if (response.getData() == null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.ok(response);
        }
    }
}