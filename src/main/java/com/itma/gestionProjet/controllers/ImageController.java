package com.itma.gestionProjet.controllers;

import com.itma.gestionProjet.dtos.ApiResponse;
import com.itma.gestionProjet.entities.Image;
import com.itma.gestionProjet.services.imp.ImageService;
import com.itma.gestionProjet.services.imp.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    ImageService imageService ;
    @Autowired
    UserService userService;
    @RequestMapping(value = "/upload" , method = RequestMethod.POST)
    public Image uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        return imageService.uplaodImage(file);
    }

    @PutMapping("/update/{imageId}")
    public Image updateImage(
            @PathVariable Long imageId,
            @RequestParam("image") MultipartFile file) throws IOException {
        return imageService.updateImage(imageId, file);
    }

    @RequestMapping(value = "/load/{id}" , method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) throws IOException
    {
        return imageService.getImage(id);
    }

    @RequestMapping(value = "/delete/{id}" , method = RequestMethod.DELETE)
    public void deleteImage(@PathVariable("id") Long id){
        imageService.deleteImage(id);
    }

    @PostMapping("/uploadFileDossier")
    public ApiResponse<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String message = imageService.uploadImageToFileSystem(file);
            return new ApiResponse<>(HttpStatus.OK.value(), "Fichier uploadé avec succès", message);
        } catch (Exception e) {
            return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erreur lors de l'upload du fichier", e.getMessage());
        }
    }


    /*
    @GetMapping("/getFile/{fileName}")
    public ResponseEntity<?> getFile(@PathVariable String fileName) throws IOException {
            byte[] image = imageService.downloadImageFromFileSystem(fileName);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(image);

    }

     */
    @GetMapping("/getFile/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName) {
        try {
            byte[] imageBytes = imageService.downloadImageFromFileSystem(fileName);
            ByteArrayResource resource = new ByteArrayResource(imageBytes);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
