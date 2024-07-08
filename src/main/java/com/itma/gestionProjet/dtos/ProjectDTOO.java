package com.itma.gestionProjet.dtos;

import com.itma.gestionProjet.entities.NormeProjet;
import com.itma.gestionProjet.entities.Project;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ProjectDTOO {
    private Project project;
    private List<MultipartFile> attachedFiles;
    private List<NormeProjet> normeProjects;

    // Getters et Setters
}
