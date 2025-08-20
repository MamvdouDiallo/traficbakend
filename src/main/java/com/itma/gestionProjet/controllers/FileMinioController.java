package com.itma.gestionProjet.controllers;

import com.itma.gestionProjet.dtos.FileUploadResponse;
import com.itma.gestionProjet.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/fileMinios")
public class FileMinioController {

    @Autowired
    private FileStorageService fileStorageService;

    // Upload retourne toujours JSON
    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = fileStorageService.uploadFile(file);
            return ResponseEntity.ok(new FileUploadResponse(200, "File uploaded successfully", fileName));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new FileUploadResponse(500, "Upload failed: " + e.getMessage(), null));
        }
    }

    // Download retourne le fichier brut
    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        try {
            byte[] fileData = fileStorageService.getFile(fileName);
            ByteArrayResource resource = new ByteArrayResource(fileData);

            return ResponseEntity.ok().contentLength(fileData.length).contentType(getMediaTypeForFileName(fileName)) // Détermine dynamiquement le type
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"").body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Méthode utilitaire pour déterminer le Content-Type
    private MediaType getMediaTypeForFileName(String fileName) {
        String contentType = "application/octet-stream"; // Par défaut

        if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg")) {
            contentType = "image/jpeg";
        } else if (fileName.toLowerCase().endsWith(".png")) {
            contentType = "image/png";
        } else if (fileName.toLowerCase().endsWith(".pdf")) {
            contentType = "application/pdf";
        }


        // Ajoutez d'autres extensions au besoin

        return MediaType.parseMediaType(contentType);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<FileUploadResponse> deleteFile(@PathVariable String fileName) {
        try {
            fileStorageService.deleteFile(fileName);
            return new ResponseEntity<>(new FileUploadResponse(200, "File deleted successfully", fileName), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new FileUploadResponse(500, "Delete failed: " + e.getMessage(), fileName), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

