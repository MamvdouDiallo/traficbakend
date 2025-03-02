package com.itma.gestionProjet.controllers;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.dtos.DatabasePapAgricoleResponseDTO;
import com.itma.gestionProjet.dtos.DatabasePapPlaceAffaireRequestDTO;
import com.itma.gestionProjet.dtos.DatabasePapPlaceAffaireResponseDTO;
import com.itma.gestionProjet.entities.DatabasePapPlaceAffaire;
import com.itma.gestionProjet.services.DatabasePapPlaceAffaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/databasePapPlaceAffaire")
public class DatabasePapPlaceAffaireController {

    @Autowired
    private DatabasePapPlaceAffaireService databasePapPlaceAffaireService;

    @PostMapping
    public ResponseEntity<AApiResponse<Object>> create(@RequestBody List<DatabasePapPlaceAffaireRequestDTO> requestDTOs) {
        try {
            databasePapPlaceAffaireService.createDatabasePapPlaceAffaire(requestDTOs);
            AApiResponse<Object> successResponse = new AApiResponse<>();
            successResponse.setResponseCode(201);
            successResponse.setData(List.of("Entities created successfully."));
            successResponse.setMessage("The entities were successfully created.");
            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
        } catch (IllegalArgumentException ex) {
            AApiResponse<Object> badRequestResponse = new AApiResponse<>();
            badRequestResponse.setResponseCode(400);
            badRequestResponse.setMessage("Invalid request: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
        } catch (Exception e) {
            AApiResponse<Object> errorResponse = new AApiResponse<>();
            errorResponse.setResponseCode(500);
            errorResponse.setMessage("An unexpected error occurred while creating entities.");
            errorResponse.setData(List.of(e.getMessage())); // Ajouter des détails pour le débogage
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }



    /*
    @GetMapping
    public AApiResponse<DatabasePapPlaceAffaireResponseDTO> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10000000") int size) {
        List<DatabasePapPlaceAffaireResponseDTO> data = databasePapPlaceAffaireService.getAllDatabasePapPlaceAffaire(page, size);
        AApiResponse<DatabasePapPlaceAffaireResponseDTO> response = new AApiResponse<>();
        response.setResponseCode(200);
        response.setData(data);
        response.setLength(databasePapPlaceAffaireService.getTotalCount());
        response.setMessage("Successfully retrieved data.");


        return response;
    }

     */

    @GetMapping
    public AApiResponse<DatabasePapPlaceAffaireResponseDTO> getAll(
            @RequestParam(required = false) Long projectId,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int max) {
        try {
            List<DatabasePapPlaceAffaireResponseDTO> data;
            long totalCount;

            if (projectId != null) {
                data = databasePapPlaceAffaireService.getDatabasePapPlaceAffaireByProjectId(projectId, offset, max);
                totalCount = databasePapPlaceAffaireService.getTotalCountByProjectId(projectId);
            } else {
                data = databasePapPlaceAffaireService.getAllDatabasePapPlaceAffaire(offset, max);
                totalCount = databasePapPlaceAffaireService.getTotalCount();
            }

            AApiResponse<DatabasePapPlaceAffaireResponseDTO> response = new AApiResponse<>();
            response.setResponseCode(200);
            response.setData(data);
            response.setOffset(offset);
            response.setLength(totalCount);
            response.setMax(max);
            response.setMessage("Successfully retrieved data.");
            return response;
        } catch (Exception e) {
            AApiResponse<DatabasePapPlaceAffaireResponseDTO> errorResponse = new AApiResponse<>();
            errorResponse.setResponseCode(500);
            errorResponse.setMessage("Error retrieving data: " + e.getMessage());
            return errorResponse;
        }
    }

    @GetMapping("/{id}")
    public DatabasePapPlaceAffaireResponseDTO getById(@PathVariable Long id) {
        return databasePapPlaceAffaireService.getDatabasePapPlaceAffaireById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AApiResponse<String>> update(@PathVariable Long id, @RequestBody DatabasePapPlaceAffaireRequestDTO requestDTO) {
        try {
            databasePapPlaceAffaireService.updateDatabasePapPlaceAffaire(id, requestDTO);
            AApiResponse<String> response = new AApiResponse<>();
            response.setResponseCode(200);
            response.setData(List.of("Entity updated successfully."));
            response.setMessage("Successfully updated entity.");

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            AApiResponse<String> errorResponse = new AApiResponse<>();
            errorResponse.setResponseCode(400);
            errorResponse.setMessage("Invalid input: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            AApiResponse<String> errorResponse = new AApiResponse<>();
            errorResponse.setResponseCode(500);
            errorResponse.setMessage("Error updating entity: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    @DeleteMapping("/{id}")
    public AApiResponse<String> delete(@PathVariable Long id) {
        databasePapPlaceAffaireService.deleteDatabasePapPlaceAffaire(id);
        AApiResponse<String> response = new AApiResponse<>();
        response.setResponseCode(200);
        response.setData(List.of("Entity deleted successfully."));
        response.setMessage("Successfully deleted entity.");
        return response;
    }

    @GetMapping("/byCodePap/{codePap}")
    public ResponseEntity<AApiResponse<DatabasePapPlaceAffaire>> getByCodePap(@PathVariable String codePap) {
        DatabasePapPlaceAffaire papPlaceAffaire = databasePapPlaceAffaireService.getByCodePap(codePap);
        AApiResponse<DatabasePapPlaceAffaire> response = new AApiResponse<>(
                200,
                List.of(papPlaceAffaire),
                0,
                1,
                "Success",
                1L
        );
        return ResponseEntity.ok(response);
    }
}
