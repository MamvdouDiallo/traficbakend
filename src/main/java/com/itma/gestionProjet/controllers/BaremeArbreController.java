package com.itma.gestionProjet.controllers;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.dtos.BaremeArbreDTO;
import com.itma.gestionProjet.requests.BaremeArbreRequest;
import com.itma.gestionProjet.services.BaremeArbreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/baremeArbres")
public class BaremeArbreController {
    @Autowired
    private BaremeArbreService baremeArbreService;

    @PostMapping
    public ResponseEntity<AApiResponse<BaremeArbreDTO>> createBaremeArbres(@RequestBody List<BaremeArbreRequest> requests) {
        AApiResponse<BaremeArbreDTO> response = baremeArbreService.createBaremeArbres(requests);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<AApiResponse<BaremeArbreDTO>> getAllBaremeArbre(Pageable pageable) {
        Page<BaremeArbreDTO> baremeArbresPage = baremeArbreService.getAllBaremeArbre(pageable);
        AApiResponse<BaremeArbreDTO> response = new AApiResponse<>(
                200,
                baremeArbresPage.getContent(),
                pageable.getPageNumber(),
                pageable.getPageSize(),
                "Liste des BaremeArbre",
                baremeArbresPage.getTotalElements()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AApiResponse<BaremeArbreDTO>> getBaremeArbreById(@PathVariable Long id) {
        BaremeArbreDTO baremeArbreDTO = baremeArbreService.getBaremeArbreById(id);
        AApiResponse<BaremeArbreDTO> response = new AApiResponse<>(
                200,
                List.of(baremeArbreDTO),
                0,
                1,
                "BaremeArbre trouvé avec succès",
                1
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AApiResponse<BaremeArbreDTO>> updateBaremeArbre(@PathVariable Long id, @RequestBody BaremeArbreRequest request) {
        BaremeArbreDTO updatedBaremeArbreDTO = baremeArbreService.updateBaremeArbre(id, request);
        AApiResponse<BaremeArbreDTO> response = new AApiResponse<>(
                200,
                List.of(updatedBaremeArbreDTO),
                0,
                1,
                "BaremeArbre mis à jour avec succès",
                1
        );
        return ResponseEntity.ok(response);
    }

    // DELETE : Supprimer un BaremeArbre par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<AApiResponse<Void>> deleteBaremeArbre(@PathVariable Long id) {
        baremeArbreService.deleteBaremeArbre(id);
        AApiResponse<Void> response = new AApiResponse<>(
                200,
                null,
                0,
                0,
                "BaremeArbre supprimé avec succès",
                0
        );
        return ResponseEntity.ok(response);
    }
}
