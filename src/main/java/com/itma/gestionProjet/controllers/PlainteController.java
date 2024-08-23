package com.itma.gestionProjet.controllers;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.dtos.PlainteDto;
import com.itma.gestionProjet.requests.PlainteRequest;
import com.itma.gestionProjet.services.PlainteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/plaintes")
public class PlainteController {

    @Autowired
    private PlainteService plainteService;

    @PostMapping
    public ResponseEntity<AApiResponse<?>> createPlainte(@RequestBody PlainteRequest plainteRequest) {
        PlainteDto createdPlainte = plainteService.createPlainte(plainteRequest);
        AApiResponse<PlainteDto> response = new AApiResponse<>();
        response.setResponseCode(201);
        response.setMessage("Plainte créée avec succès");
        response.setData(Collections.singletonList(createdPlainte));
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<AApiResponse<PlainteDto>> getAllPlaintes(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int max) {

        Pageable pageable = (Pageable) PageRequest.of(offset, max);
        Page<PlainteDto> plaintePage = plainteService.getAllPlaintes(pageable);
        AApiResponse<PlainteDto> response = new AApiResponse<>();
        response.setResponseCode(200);
        response.setMessage("Liste des plaintes récupérée avec succès");
        response.setData(((plaintePage.getContent())));// Ne pas encapsuler dans une liste supplémentaire
        response.setOffset(offset);
        response.setMax(max);
        response.setLength(plaintePage.getTotalElements());

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<AApiResponse<PlainteDto>> getPlainteById(@PathVariable Long id) {
        PlainteDto plainte = plainteService.getPlainteById(id);

        AApiResponse<PlainteDto> response = new AApiResponse<>();
        response.setResponseCode(200);
        response.setMessage("Plainte récupérée avec succès");
        response.setData((List<PlainteDto>) plainte);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AApiResponse<PlainteDto>> updatePlainte(@PathVariable Long id, @RequestBody PlainteRequest plainteRequest) {
        PlainteDto updatedPlainte = plainteService.updatePlainte(id, plainteRequest);

        AApiResponse<PlainteDto> response = new AApiResponse<>();
        response.setResponseCode(200);
        response.setMessage("Plainte mise à jour avec succès");
        response.setData((List<PlainteDto>) updatedPlainte);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AApiResponse<Void>> deletePlainte(@PathVariable Long id) {
        plainteService.deletePlainte(id);

        AApiResponse<Void> response = new AApiResponse<>();
        response.setResponseCode(204);
        response.setMessage("Plainte supprimée avec succès");

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-codePap")
    public ResponseEntity<AApiResponse<PlainteDto>> getPlainteByCodePap(@RequestParam String codePap,
                                                                        @RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size) {
        AApiResponse<PlainteDto> response = plainteService.getPlainteByCodePap(codePap, page, size);
        return ResponseEntity.status(response.getResponseCode()).body(response);
    }
}