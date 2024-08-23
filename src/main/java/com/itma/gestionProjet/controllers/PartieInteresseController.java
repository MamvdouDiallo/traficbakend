package com.itma.gestionProjet.controllers;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.dtos.PartieInteresseDTO;
import com.itma.gestionProjet.dtos.PartieInteresseResponseDTO;
import com.itma.gestionProjet.entities.PartieInteresse;
import com.itma.gestionProjet.exceptions.PartieInteresseNotFoundException;
import com.itma.gestionProjet.services.PartieInteresseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/partie-interesse")
public class PartieInteresseController {

    @Autowired
    private PartieInteresseService service;

    @GetMapping
    public ResponseEntity<AApiResponse<PartieInteresseResponseDTO>> getAll(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int max,
            @RequestParam(required = false) String categorieLibelle) {

        Pageable pageable = PageRequest.of(offset, max);
        Page<PartieInteresse> partieInteressePage;

        if (categorieLibelle != null) {
            partieInteressePage = service.findByCategoriePartieInteresseLibelle(categorieLibelle, pageable);
        } else {
            partieInteressePage = service.getPartieInteresses(pageable);
        }

        AApiResponse<PartieInteresseResponseDTO> response = new AApiResponse<>();
        response.setResponseCode(200);

        // Convertir les entités PartieInteresse en DTO
        List<PartieInteresseResponseDTO> dtoList = partieInteressePage.getContent().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        response.setData(dtoList);
        response.setOffset(offset);
        response.setMax(max);
        response.setLength(partieInteressePage.getTotalElements());

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public Optional<PartieInteresse> getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<AApiResponse<PartieInteresseResponseDTO>> create(@RequestBody PartieInteresseDTO partieInteresseDTO) {
        AApiResponse<PartieInteresseResponseDTO> response = new AApiResponse<>();
        try {
            PartieInteresse savedPartieInteresse = service.save(partieInteresseDTO);
            PartieInteresseResponseDTO dto = this.mapToDTO(savedPartieInteresse);
            response.setResponseCode(200);
            response.setMessage("Partie intéressée créée avec succès");
            response.setData(Collections.singletonList(dto)); // Encapsulez le DTO dans une liste
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.setResponseCode(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }



    @PutMapping("/{id}")
    public ResponseEntity<AApiResponse<PartieInteresseResponseDTO>> update(
            @PathVariable Long id,
            @RequestBody PartieInteresseDTO partieInteresse) {
        AApiResponse<PartieInteresseResponseDTO> response = new AApiResponse<>();
        try {
            PartieInteresse updatedPip = service.update(id, partieInteresse);
            PartieInteresseResponseDTO dto = this.mapToDTO(updatedPip);
            response.setResponseCode(200);
            response.setMessage("Partie intéressée mise à jour avec succès");
            response.setData(Collections.singletonList(dto));  // Assurez-vous que setData accepte un objet unique
            return ResponseEntity.ok().body(response);
        } catch (PartieInteresseNotFoundException e) {
            response.setResponseCode(404);
            response.setMessage("Partie intéressée non trouvée : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setResponseCode(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    /*
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<AApiResponse<Void>> delete(@PathVariable Long id) {
        AApiResponse<Void> response = new AApiResponse<>();
        try {
            service.deleteById(id);
            response.setResponseCode(200);
            response.setMessage("Partie intéressée supprimée avec succès");
            return ResponseEntity.ok().body(response);
        } catch (PartieInteresseNotFoundException e) {
            response.setResponseCode(404);
            response.setMessage("Partie intéressée non trouvée : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setResponseCode(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }



     PartieInteresseResponseDTO mapToDTO(PartieInteresse partieInteresse) {
        PartieInteresseResponseDTO dto = new PartieInteresseResponseDTO();
        dto.setId(partieInteresse.getId());
        dto.setAdresse(partieInteresse.getAdresse());
        dto.setAdresseContactPrincipal(partieInteresse.getUser().getLocality());
        dto.setCategoriePartieInteresse(partieInteresse.getCategoriePartieInteresse().getId());
        dto.setCourielPrincipal(partieInteresse.getCourrielPrincipal());
        dto.setDateNaissanceContactPrincipal(partieInteresse.getUser().getDate_of_birth());
        dto.setEmailContactPrincipal(partieInteresse.getUser().getEmail());
        dto.setLibelle(partieInteresse.getLibelle());
        dto.setLieuNaisasnceContactPrincipal(partieInteresse.getUser().getPlace_of_birth());
        dto.setLocalisation(partieInteresse.getLocalisation());
        dto.setNomContactPrincipal(partieInteresse.getUser().getLastname());
        dto.setNormes(partieInteresse.getNormes());
        dto.setPrenomContactPrincipal(partieInteresse.getUser().getFirstname());
        dto.setSexeContactPrincipal(partieInteresse.getUser().getSexe());
        dto.setStatut(partieInteresse.getStatut());
        dto.setTelephoneContactPrincipal(partieInteresse.getUser().getContact());
        dto.setProject_id((long) partieInteresse.getProject().getId());
        return dto;
    }



}