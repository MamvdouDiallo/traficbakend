package com.itma.gestionProjet.controllers;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.dtos.CompensationDTO;
import com.itma.gestionProjet.requests.CompensationRequest;
import com.itma.gestionProjet.services.CompensationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compensations")
public class CompensationController {

    private final CompensationService service;

    public CompensationController(CompensationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<AApiResponse<CompensationDTO>> getAllCompensations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<CompensationDTO> pageResult = service.getAllCompensations(pageable);

            // Construire la réponse AApiResponse
            AApiResponse<CompensationDTO> response = new AApiResponse<>();
            response.setResponseCode(200);
            response.setData(pageResult.getContent());
            response.setOffset(page);
            response.setMax(size);
            response.setMessage("Liste des compensations récupérée avec succès.");
            response.setLength(pageResult.getTotalElements());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return buildErrorResponse(e, "Erreur lors de la récupération des compensations");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AApiResponse<CompensationDTO>> getCompensationById(@PathVariable Long id) {
        try {
            CompensationDTO dto = service.getCompensationById(id);

            // Construire la réponse AApiResponse
            AApiResponse<CompensationDTO> response = new AApiResponse<>();
            response.setResponseCode(200);
            response.setData(List.of(dto));
            response.setMessage("Compensation récupérée avec succès.");
            response.setLength(1);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return buildErrorResponse(e, "Erreur lors de la récupération de la compensation");
        }
    }

    @PostMapping
    public ResponseEntity<AApiResponse<CompensationDTO>> createCompensation(@RequestBody CompensationRequest request) {
        try {
            CompensationDTO dto = service.createCompensation(request);

            // Construire la réponse AApiResponse
            AApiResponse<CompensationDTO> response = new AApiResponse<>();
            response.setResponseCode(201);
            response.setData(List.of(dto));
            response.setMessage("Compensation créée avec succès.");
            response.setLength(1);

            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            return buildErrorResponse(e, "Erreur lors de la création de la compensation");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AApiResponse<CompensationDTO>> updateCompensation(
            @PathVariable Long id,
            @RequestBody CompensationRequest request
    ) {
        try {
            CompensationDTO dto = service.updateCompensation(id, request);

            // Construire la réponse AApiResponse
            AApiResponse<CompensationDTO> response = new AApiResponse<>();
            response.setResponseCode(200);
            response.setData(List.of(dto));
            response.setMessage("Compensation mise à jour avec succès.");
            response.setLength(1);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return buildErrorResponse(e, "Erreur lors de la mise à jour de la compensation");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AApiResponse<Void>> deleteCompensation(@PathVariable Long id) {
        try {
            service.deleteCompensation(id);

            // Construire la réponse AApiResponse
            AApiResponse<Void> response = new AApiResponse<>();
            response.setResponseCode(200);
            response.setData(null);
            response.setMessage("Compensation supprimée avec succès.");
            response.setLength(0);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return buildErrorResponse(e, "Erreur lors de la suppression de la compensation");
        }
    }

    // Méthode utilitaire pour construire une réponse d'erreur
    private <T> ResponseEntity<AApiResponse<T>> buildErrorResponse(Exception e, String defaultMessage) {
        AApiResponse<T> errorResponse = new AApiResponse<>();
        errorResponse.setResponseCode(500);
        errorResponse.setData(null);
        errorResponse.setMessage(defaultMessage + " : " + e.getMessage());
        errorResponse.setLength(0);
        return ResponseEntity.status(500).body(errorResponse);
    }
}


