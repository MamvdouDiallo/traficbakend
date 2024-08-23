package com.itma.gestionProjet.controllers;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.entities.Coproprietaire;
import com.itma.gestionProjet.entities.Tache;
import com.itma.gestionProjet.requests.CoproprietaireRequest;
import com.itma.gestionProjet.services.CoproprietaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/coproprietaires")
public class CoproprietaireController {
    @Autowired
    private CoproprietaireService coproprietaireService;

    @GetMapping
    public AApiResponse<Coproprietaire> getAllCoproprietaires(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int max) {
        return coproprietaireService.getAllCoproprietaires(offset, max);
    }

    @GetMapping("/{id}")
    public AApiResponse<Coproprietaire> getCoproprietaireById(@PathVariable Long id) {
        return coproprietaireService.getCoproprietaireById(id);
    }

    @PostMapping

    public ResponseEntity<AApiResponse<List<CoproprietaireRequest>>> createTache(@RequestBody List<CoproprietaireRequest> coproprietaireRequests) {
        AApiResponse<List<CoproprietaireRequest>> response = new AApiResponse<>();
        try {
            List<CoproprietaireRequest> savedCoproprietaires = coproprietaireService.createCoproprietaire(coproprietaireRequests);
            response.setResponseCode(201);
            response.setData(Collections.singletonList(savedCoproprietaires));
            response.setMessage("Copropriétaires créés avec succès");
            response.setLength(savedCoproprietaires.size());
            response.setOffset(0);
            response.setMax(0);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setResponseCode(500);
            response.setMessage("Erreur lors de la création des copropriétaires : " + e.getMessage());
            response.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public AApiResponse<Coproprietaire> updateCoproprietaire(@PathVariable Long id, @RequestBody Coproprietaire coproprietaire) {
        return coproprietaireService.updateCoproprietaire(id, coproprietaire);
    }

    @DeleteMapping("/{id}")
    public AApiResponse<Void> deleteCoproprietaire(@PathVariable Long id) {
        return coproprietaireService.deleteCoproprietaire(id);
    }

    @GetMapping("/by-codePap")
    public ResponseEntity<AApiResponse<List<Coproprietaire>>> getCoproprietairesByCodePap(@RequestParam String codePap,
                                                                                          @RequestParam(defaultValue = "0") int offset,
                                                                                          @RequestParam(defaultValue = "100") int max) {
        AApiResponse<List<Coproprietaire>> response = coproprietaireService.getCoproprietairesByCodePap(codePap, offset, max);

        if (response.getData() == null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.ok(response);
        }
    }
}
