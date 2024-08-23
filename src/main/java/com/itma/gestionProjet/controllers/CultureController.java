package com.itma.gestionProjet.controllers;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.dtos.CultureDTO;
import com.itma.gestionProjet.entities.Coproprietaire;
import com.itma.gestionProjet.entities.Culture;
import com.itma.gestionProjet.requests.CultureRequest;
import com.itma.gestionProjet.services.CultureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/cultures")
public class CultureController {

    @Autowired
    private CultureService cultureService;

    @GetMapping()
    public AApiResponse<Culture> getAllCultures(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int max) {
        return cultureService.getAllCultures(offset, max);
    }
    @PostMapping()
    public ResponseEntity<AApiResponse<List<CultureDTO>>> createCulture(@RequestBody List<CultureRequest> cultureRequests) {
        AApiResponse<List<CultureDTO>> response = new AApiResponse<>();
        try {
            List<CultureDTO> cultureDTOs = cultureService.createCulture(cultureRequests);
            response.setResponseCode(HttpStatus.CREATED.value());
            response.setData(Collections.singletonList(cultureDTOs));
            response.setMessage("Cultures créées avec succès");
            response.setLength(cultureDTOs.size());
            response.setOffset(0); // Vous pouvez ajuster l'offset si nécessaire
            response.setMax(cultureRequests.size());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Erreur lors de la création des cultures : " + e.getMessage());
            response.setData(Collections.emptyList());
            response.setLength(0);
            response.setOffset(0);
            response.setMax(cultureRequests.size());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/by-codePap")
    public ResponseEntity<AApiResponse<List<Culture>>> getCulturesByCodePap(@RequestParam String codePap,
                                                                            @RequestParam(defaultValue = "0") int offset,
                                                                            @RequestParam(defaultValue = "10") int max) {
        AApiResponse<List<Culture>> response = cultureService.getCulturesByCodePap(codePap, offset, max);

        if (response.getData() == null) {
            return ResponseEntity.status(response.getResponseCode()).body(response);
        } else {
            return ResponseEntity.ok(response);
        }
    }
}
