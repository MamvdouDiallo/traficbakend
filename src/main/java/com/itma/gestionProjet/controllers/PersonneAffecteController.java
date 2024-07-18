package com.itma.gestionProjet.controllers;


import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.dtos.ApiResponse;
import com.itma.gestionProjet.entities.PersonneAffecte;
import com.itma.gestionProjet.services.ExcelService;
import com.itma.gestionProjet.services.imp.PersonneAffecteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/personneAffectes")
public class PersonneAffecteController {

    @Autowired
    private PersonneAffecteServiceImpl personneAffecteService;

    @GetMapping
    public ResponseEntity<AApiResponse<PersonneAffecte>> getPersonneAffectes(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int max) {
        Page<PersonneAffecte> personneAffectePage = personneAffecteService.getPersonneAffectes(offset, max);
        AApiResponse<PersonneAffecte> response = new AApiResponse<>();
        response.setResponseCode(200);
        response.setData(personneAffectePage.getContent());
        response.setOffset(offset);
        response.setMax(max);
        response.setLength(personneAffectePage.getTotalElements());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonneAffecte> getPersonneAffecteById(@PathVariable Long id) {
        Optional<PersonneAffecte> personneAffecte = personneAffecteService.getPersonneAffecteById(id);
        return personneAffecte.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AApiResponse<PersonneAffecte>> createPersonneAffecte(@RequestBody PersonneAffecte personneAffecte) {
        AApiResponse<PersonneAffecte> response = new AApiResponse<>();
        try {
            PersonneAffecte savedPersonneAffecte = personneAffecteService.savePersonneAffecte(personneAffecte);
            response.setResponseCode(200);
            response.setMessage("Personne affecté créé avec succés");
            response.setData(Collections.singletonList(savedPersonneAffecte));
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.setResponseCode(500); // or any other appropriate error code
            response.setMessage("An error occurred while saving the PersonneAffecte: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @PostMapping("/importer")
    public ResponseEntity<ApiResponse<List<PersonneAffecte>>> createPersonneAffectes(@RequestBody List<PersonneAffecte> personneAffectes) {
        try {
            List<PersonneAffecte> savedPersonneAffectes = personneAffecteService.savePersonneAffectes(personneAffectes);
            ApiResponse<List<PersonneAffecte>> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Personnes affectées importées avec succès", savedPersonneAffectes);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ApiResponse<List<PersonneAffecte>> errorResponse = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erreur lors de la création des personnes affectées", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }



    @PutMapping("/{id}")
    public ResponseEntity<PersonneAffecte> updatePersonneAffecte(@PathVariable Long id, @RequestBody PersonneAffecte personneAffecteDetails) {
        Optional<PersonneAffecte> personneAffecte = personneAffecteService.getPersonneAffecteById(id);
        if (personneAffecte.isPresent()) {
            PersonneAffecte updatedPersonneAffecte = personneAffecte.get();
            updatedPersonneAffecte.setIdPap(personneAffecteDetails.getIdPap());
            updatedPersonneAffecte.setNombreParcelle(personneAffecteDetails.getNombreParcelle());
            updatedPersonneAffecte.setIdParcelle(personneAffecteDetails.getIdParcelle());
            updatedPersonneAffecte.setCategorie(personneAffecteDetails.getCategorie());
            updatedPersonneAffecte.setPrenom(personneAffecteDetails.getPrenom());
            updatedPersonneAffecte.setNom(personneAffecteDetails.getNom());
            updatedPersonneAffecte.setDateNaissance(personneAffecteDetails.getDateNaissance());
            updatedPersonneAffecte.setLieuNaissance(personneAffecteDetails.getLieuNaissance());
            updatedPersonneAffecte.setSexe(personneAffecteDetails.getSexe());
            updatedPersonneAffecte.setAge(personneAffecteDetails.getAge());
            updatedPersonneAffecte.setNationalite(personneAffecteDetails.getNationalite());
            updatedPersonneAffecte.setDepartement(personneAffecteDetails.getDepartement());
            updatedPersonneAffecte.setNumeroIdentification(personneAffecteDetails.getNumeroIdentification());
            updatedPersonneAffecte.setNumeroTelephone(personneAffecteDetails.getNumeroTelephone());
            updatedPersonneAffecte.setLocaliteResidence(personneAffecteDetails.getLocaliteResidence());
            updatedPersonneAffecte.setStatutPap(personneAffecteDetails.getStatutPap());
            updatedPersonneAffecte.setStatutVulnerable(personneAffecteDetails.getStatutVulnerable());
            updatedPersonneAffecte.setPrenomExploitant(personneAffecteDetails.getPrenomExploitant());
            updatedPersonneAffecte.setNomExploitant(personneAffecteDetails.getNomExploitant());
            updatedPersonneAffecte.setSuperficieAffecte(personneAffecteDetails.getSuperficieAffecte());
            updatedPersonneAffecte.setTypeCulture(personneAffecteDetails.getTypeCulture());
            updatedPersonneAffecte.setTypeEquipement(personneAffecteDetails.getTypeEquipement());
            updatedPersonneAffecte.setSuperficieCultive(personneAffecteDetails.getSuperficieCultive());
            updatedPersonneAffecte.setDescriptionBienAffecte(personneAffecteDetails.getDescriptionBienAffecte());

            PersonneAffecte savedPersonneAffecte = personneAffecteService.savePersonneAffecte(updatedPersonneAffecte);
            return ResponseEntity.ok(savedPersonneAffecte);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonneAffecte(@PathVariable Long id) {
        personneAffecteService.deletePersonneAffecte(id);
        return ResponseEntity.noContent().build();
    }


    @Autowired
    private ExcelService excelService;

    @PostMapping("/import-personnes-affectees")
    public ResponseEntity<String> importPersonneAffecteData(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload a file!");
        }
        try {
            excelService.importPersonneAffecteData(file);
            return ResponseEntity.status(HttpStatus.OK).body("File imported successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to import data: " + e.getMessage());
        }
    }
}