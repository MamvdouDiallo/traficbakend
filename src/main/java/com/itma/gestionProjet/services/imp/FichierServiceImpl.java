package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.entities.Fichier;
import com.itma.gestionProjet.entities.Project;
import com.itma.gestionProjet.repositories.FichierRepository;
import com.itma.gestionProjet.repositories.ProjectRepository;
import com.itma.gestionProjet.services.IFichierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class FichierServiceImpl  implements IFichierService {
    @Autowired
    private FichierRepository fichierRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Fichier addFichier(Long projectId, Fichier fichier) {
        // Récupérer le projet par son ID
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + projectId));

        // Associer le fichier au projet
        fichier.setProject(project);

        // Sauvegarder le fichier
        return fichierRepository.save(fichier);
    }


    @Override
    public Fichier getFichierById(Long id) {
        Optional<Fichier> fichier = fichierRepository.findById(id);
        if (fichier.isPresent()) {
            return fichier.get();
        } else {
            throw new RuntimeException("Fichier not found with ID: " + id);
        }
    }

    @Override
    public List<Fichier> getAllFichiers() {
        return fichierRepository.findAll();
    }

    @Override
    public Fichier updateFichier(Long id, Fichier fichierDetails) {
        Fichier fichier = getFichierById(id);
        fichier.setFichierUrl(fichierDetails.getFichierUrl());
        fichier.setProject(fichierDetails.getProject());
        return fichierRepository.save(fichier);
    }

    @Override
    public void deleteFichier(Long id) {
        Fichier fichier = getFichierById(id);
        fichierRepository.delete(fichier);
    }

    @Override
    public List<Fichier> getFichiersByProjectId(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + projectId));
        return fichierRepository.findByProject(project);
    }
}
