package com.itma.gestionProjet.controllers;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.dtos.EntenteCompensationDto;
import com.itma.gestionProjet.services.EntenteCompensationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/entente_compensations")
public class EntenteCompensationController {

    @Autowired
    private EntenteCompensationService ententeCompensationService;


    @PostMapping
    public ResponseEntity<AApiResponse<EntenteCompensationDto>> createEntenteCompensation(@RequestBody EntenteCompensationDto ententeCompensationDto) {
        AApiResponse<EntenteCompensationDto> response = new AApiResponse<>();
        try {
            EntenteCompensationDto savedEntenteCompensation = ententeCompensationService.createEntenteCompensation(ententeCompensationDto);
            response.setResponseCode(200);
            response.setMessage("EntenteCompensation créée avec succès");
            response.setData(Collections.singletonList(savedEntenteCompensation));
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.setResponseCode(500); // ou tout autre code d'erreur approprié
            response.setMessage("Une erreur s'est produite lors de la création de l'EntenteCompensation : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<EntenteCompensationDto> getEntenteCompensationById(@PathVariable Long id) {
        EntenteCompensationDto ententeCompensation = ententeCompensationService.getEntenteCompensationById(id);
        return ResponseEntity.ok(ententeCompensation);
    }



    @GetMapping
    public ResponseEntity<AApiResponse<EntenteCompensationDto>> getAllEntenteCompensations(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int max) {

        Pageable pageable = PageRequest.of(offset, max);
        Page<EntenteCompensationDto> ententeCompensationPage = ententeCompensationService.getAllEntenteCompensations(pageable);

        // Créer une réponse enveloppée dans un objet AApiResponse
        AApiResponse<EntenteCompensationDto> response = new AApiResponse<>();
        response.setResponseCode(200);
        response.setMessage("Liste des ententes de compensation récupérée avec succès");
        response.setData(ententeCompensationPage.getContent());
        response.setOffset(offset);
        response.setMax(max);
        response.setLength(ententeCompensationPage.getTotalElements());

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntenteCompensation(@PathVariable Long id) {
        ententeCompensationService.deleteEntenteCompensation(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/by-codePap")
    public ResponseEntity<AApiResponse<EntenteCompensationDto>> getEntenteByCodePap(@RequestParam String codePap) {
        AApiResponse<EntenteCompensationDto> response = ententeCompensationService.getEntenteCompensationByCodePap(codePap);
        return ResponseEntity.status(response.getResponseCode()).body(response);
    }
}
