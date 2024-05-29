package com.itma.gestionProjet.controllers;

import com.itma.gestionProjet.entities.Image;
import com.itma.gestionProjet.services.imp.ImageService;
import com.itma.gestionProjet.services.imp.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/load/{id}" , method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) throws IOException
    {
        return imageService.getImage(id);
    }

    @RequestMapping(value = "/delete/{id}" , method = RequestMethod.DELETE)
    public void deleteImage(@PathVariable("id") Long id){
        imageService.deleteImage(id);
    }



    @RequestMapping(value="/update",method = RequestMethod.PUT)
    public Image UpdateImage(@RequestParam("image")MultipartFile file) throws IOException {
        return imageService.uplaodImage(file);
    }
}
