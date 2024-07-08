package com.itma.gestionProjet.services;

import com.itma.gestionProjet.dtos.ProjectDTO;
import com.itma.gestionProjet.dtos.UserDTO;
import com.itma.gestionProjet.entities.Project;
import com.itma.gestionProjet.requests.ProjectRequest;

import java.util.List;
import java.util.Optional;

public interface IProjectService {
    Optional<Project> findProjectByName(String name);

    ProjectDTO saveProject(Project p);
    ProjectDTO updateProject(Project p);
    Optional<ProjectDTO> findProjectById(Long id);


    List<ProjectDTO> getAllProjects();
    void deleteProject(Project p);
    void deleteProjectById(Long id);

    ProjectDTO convertEntityToDto(Project p);
    Project convertDtoToEntity(Project ProjectDto);
}
