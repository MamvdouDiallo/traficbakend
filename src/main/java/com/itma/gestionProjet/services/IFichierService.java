package com.itma.gestionProjet.services;

import com.itma.gestionProjet.entities.Fichier;
import com.itma.gestionProjet.entities.File;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IFichierService {
    Fichier addFichier(Long projectId, Fichier fichier);

    Fichier getFichierById(Long id);

    List<Fichier> getAllFichiers();

    Fichier updateFichier(Long id, Fichier fichier);

    void deleteFichier(Long id);

    List<Fichier> getFichiersByProjectId(Long projectId);

}
