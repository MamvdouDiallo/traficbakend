package com.itma.gestionProjet.controllers;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.dtos.DatabasePapAgricoleRequestDTO;
import com.itma.gestionProjet.dtos.DatabasePapAgricoleResponseDTO;
import com.itma.gestionProjet.entities.DatabasePapAgricole;
import com.itma.gestionProjet.services.DatabasePapAgricoleService;
import com.itma.gestionProjet.services.imp.ExcelImportDatabasePapAgricoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/papAgricole")
public class PapAgricoleController {

    @Autowired
    private ExcelImportDatabasePapAgricoleService excelImportService;

    @Autowired
    private DatabasePapAgricoleService databasePapAgricoleService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadExcel(@RequestParam("file") MultipartFile file) {
        try {
            ByteArrayInputStream errorFile = excelImportService.importExcel(file);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=errors.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(errorFile.readAllBytes());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public AApiResponse<DatabasePapAgricoleResponseDTO> getAll(
            @RequestParam(defaultValue = "0") int offset, // Valeur par défaut 0 pour la page
            @RequestParam(defaultValue = "100000000") int max) { // Valeur par défaut 10 pour la taille
        try {
            // Appel au service pour récupérer les données
            List<DatabasePapAgricoleResponseDTO> data = databasePapAgricoleService.getAllDatabasePapAgricole(offset, max);

            // Création de la réponse AApiResponse
            AApiResponse<DatabasePapAgricoleResponseDTO> response = new AApiResponse<>();
            response.setResponseCode(200);
            response.setData(data);
            response.setOffset(offset);
            response.setLength(databasePapAgricoleService.getTotalCount());
            response.setMax(max);
            response.setMessage("Successfully retrieved data.");

            return response;
        } catch (Exception e) {
            // Gérer les erreurs et renvoyer un message d'erreur avec le code approprié
            AApiResponse<DatabasePapAgricoleResponseDTO> errorResponse = new AApiResponse<>();
            errorResponse.setResponseCode(500);
            errorResponse.setMessage("Error retrieving data: " + e.getMessage());
            return errorResponse;
        }
    }

    // Méthode GET pour récupérer une entité par ID
    @GetMapping("/{id}")
    public AApiResponse<DatabasePapAgricoleResponseDTO> getById(@PathVariable Long id) {
        try {
            // Appel au service pour récupérer les données par ID
            DatabasePapAgricole data = databasePapAgricoleService.getDatabasePapAgricoleById(id);

            // Création de la réponse AApiResponse
            AApiResponse<DatabasePapAgricoleResponseDTO> response = new AApiResponse<>();
            response.setResponseCode(200);
            response.setData(List.of());
            response.setMessage("Successfully retrieved data.");
            response.setLength(1);

            return response;
        } catch (Exception e) {
            // Gérer l'erreur et renvoyer un message d'erreur
            AApiResponse<DatabasePapAgricoleResponseDTO> errorResponse = new AApiResponse<>();
            errorResponse.setResponseCode(404);
            errorResponse.setMessage("Entity not found: " + e.getMessage());
            return errorResponse;
        }
    }

    // Méthode POST pour créer de nouvelles entités
    @PostMapping
    public ResponseEntity<AApiResponse<String>> create(@RequestBody List<DatabasePapAgricoleRequestDTO> requestDTOs) {
        try {
            databasePapAgricoleService.createDatabasePapAgricole(requestDTOs);
            AApiResponse<String> response = new AApiResponse<>();
            response.setResponseCode(HttpStatus.CREATED.value());
            response.setData(List.of("Les entités ont été créées avec succès."));
            response.setMessage("La création des entités a été réalisée avec succès.");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            AApiResponse<String> errorResponse = new AApiResponse<>();
            errorResponse.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.setMessage("Une erreur est survenue lors de la création des entités : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    // Méthode PUT pour mettre à jour une entité
    @PutMapping("/{id}")
    public ResponseEntity<AApiResponse<String>> update(@PathVariable Long id, @RequestBody DatabasePapAgricoleRequestDTO requestDTO) {
        try {
            // Appel au service pour mettre à jour l'entité
            databasePapAgricoleService.updateDatabasePapAgricole(id, requestDTO);

            // Création de la réponse AApiResponse pour succès
            AApiResponse<String> response = new AApiResponse<>();
            response.setResponseCode(200);
            response.setData(List.of("Entity updated successfully."));
            response.setMessage("Successfully updated entity.");

            return ResponseEntity.ok(response); // Code HTTP 200 OK
        } catch (Exception e) {
            // Gérer les erreurs et renvoyer un message d'erreur avec le bon code HTTP
            AApiResponse<String> errorResponse = new AApiResponse<>();
            errorResponse.setResponseCode(500);
            errorResponse.setMessage("Error updating entity: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse); // Code HTTP 500
        }
    }


    // Méthode DELETE pour supprimer une entité
    @DeleteMapping("/{id}")
    public AApiResponse<String> delete(@PathVariable Long id) {
        try {
            // Appel au service pour supprimer l'entité
            databasePapAgricoleService.deleteDatabasePapAgricole(id);

            // Création de la réponse AApiResponse pour succès
            AApiResponse<String> response = new AApiResponse<>();
            response.setResponseCode(200);
            response.setData(List.of("Entity deleted successfully."));
            response.setMessage("Successfully deleted entity.");

            return response;
        } catch (Exception e) {
            // Gérer les erreurs et renvoyer un message d'erreur
            AApiResponse<String> errorResponse = new AApiResponse<>();
            errorResponse.setResponseCode(500);
            errorResponse.setMessage("Error deleting entity: " + e.getMessage());
            return errorResponse;
        }
    }


    @GetMapping("/byCodePap/{codePap}")
    public ResponseEntity<AApiResponse<DatabasePapAgricole>> getByCodePap(@PathVariable String codePap) {
        DatabasePapAgricole papAgricole = databasePapAgricoleService.getByCodePap(codePap);
        AApiResponse<DatabasePapAgricole> response = new AApiResponse<>(
                200, // responseCode
                List.of(papAgricole), // data
                0, // offset
                1, // max
                "Success", // message
                1L // length
        );
        return ResponseEntity.ok(response);
    }
}

