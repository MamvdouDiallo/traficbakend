package com.itma.gestionProjet.controllers;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.dtos.CategorieDocumentDTO;
import com.itma.gestionProjet.requests.CategorieDocumentRequest;
import com.itma.gestionProjet.services.CategorieDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorieDocuments")
public class CategorieDocumentController {

    @Autowired
    private CategorieDocumentService categorieDocumentService;

    @PostMapping
    public ResponseEntity<AApiResponse<CategorieDocumentDTO>> createCategorieDocument(@RequestBody CategorieDocumentRequest categorieDocumentRequest) {
        AApiResponse<CategorieDocumentDTO> response = new AApiResponse<>();
        try {
            CategorieDocumentDTO categorieDocumentDTO = categorieDocumentService.createCategorieDocument(categorieDocumentRequest);
            response.setResponseCode(HttpStatus.CREATED.value());
            response.setData(List.of(categorieDocumentDTO));
            response.setMessage("CategorieDocument created successfully");
            response.setLength(1);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error creating CategorieDocument: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AApiResponse<CategorieDocumentDTO>> updateCategorieDocument(
            @PathVariable Long id,
            @RequestBody CategorieDocumentRequest categorieDocumentRequest) {
        AApiResponse<CategorieDocumentDTO> response = new AApiResponse<>();
        try {
            CategorieDocumentDTO categorieDocumentDTO = categorieDocumentService.updateCategorieDocument(id, categorieDocumentRequest);
            response.setResponseCode(HttpStatus.OK.value());
            response.setData(List.of(categorieDocumentDTO));
            response.setMessage("CategorieDocument updated successfully");
            response.setLength(1);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            response.setResponseCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("CategorieDocument not found: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error updating CategorieDocument: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AApiResponse<Void>> deleteCategorieDocument(@PathVariable Long id) {
        AApiResponse<Void> response = new AApiResponse<>();
        try {
            categorieDocumentService.deleteCategorieDocument(id);
            response.setResponseCode(HttpStatus.NO_CONTENT.value());
            response.setMessage("CategorieDocument deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            response.setResponseCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("CategorieDocument not found: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error deleting CategorieDocument: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AApiResponse<CategorieDocumentDTO>> getCategorieDocumentById(@PathVariable Long id) {
        AApiResponse<CategorieDocumentDTO> response = new AApiResponse<>();
        try {
            CategorieDocumentDTO categorieDocumentDTO = categorieDocumentService.getCategorieDocumentById(id);
            response.setResponseCode(HttpStatus.OK.value());
            response.setData(List.of(categorieDocumentDTO));
            response.setMessage("CategorieDocument retrieved successfully");
            response.setLength(1);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            response.setResponseCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("CategorieDocument not found: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error retrieving CategorieDocument: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<AApiResponse<CategorieDocumentDTO>> getAllCategorieDocuments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        AApiResponse<CategorieDocumentDTO> response = new AApiResponse<>();
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<CategorieDocumentDTO> categorieDocumentsPage = categorieDocumentService.getAllCategorieDocuments(pageable);
            response.setResponseCode(HttpStatus.OK.value());
            response.setData(categorieDocumentsPage.getContent());
            response.setOffset(page);
            response.setMax(size);
            response.setMessage("CategorieDocuments retrieved successfully");
            response.setLength(categorieDocumentsPage.getTotalElements());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error retrieving CategorieDocuments: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
