package com.itma.gestionProjet.controllers;


import com.itma.gestionProjet.entities.File;
import com.itma.gestionProjet.services.imp.FileService;
import com.itma.gestionProjet.services.imp.ImageService;
import com.itma.gestionProjet.services.imp.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {


    @Autowired
    FileService fileService ;

    @Autowired
    ProjectService projectService;

    @PostMapping(value = "/uplaodFilesProd/{idProd}" )
    public File uploadMultiImages(@RequestParam("file") MultipartFile file,
                                  @PathVariable("idProd") Long idProd)
            throws IOException {
        return fileService.uplaodFileProj(file,idProd);
    }

    public List<File> getImagesProd(@PathVariable("idProd") Long idProd)
            throws IOException {
        return fileService.getFilesParProj(idProd);
    }




}
