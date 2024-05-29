package com.itma.gestionProjet.controllers;


import com.itma.gestionProjet.dtos.ApiResponse;
import com.itma.gestionProjet.dtos.ProjectDTO;
import com.itma.gestionProjet.dtos.UserDTO;
import com.itma.gestionProjet.entities.Project;
import com.itma.gestionProjet.entities.Role;
import com.itma.gestionProjet.entities.User;
import com.itma.gestionProjet.events.RegistrationCompleteEvent;
import com.itma.gestionProjet.exceptions.EmailAlreadyExistsException;
import com.itma.gestionProjet.exceptions.ProjectAlreadyExistException;
import com.itma.gestionProjet.repositories.ProjectRepository;
import com.itma.gestionProjet.repositories.UserRepository;
import com.itma.gestionProjet.requests.ProjectRequest;
import com.itma.gestionProjet.requests.UserRequest;
import com.itma.gestionProjet.services.imp.ProjectService;
import com.itma.gestionProjet.services.imp.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/projects")
@Validated
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public ApiResponse<List<ProjectDTO>> getUsers() {
        List<ProjectDTO> projects = projectService.getAllProjects();
        return new ApiResponse<>(HttpStatus.OK.value(), "Liste des utilisateurs récupérée avec succès", projects);
    }


    @RequestMapping(path = "/createProject", method = RequestMethod.POST)
    public ApiResponse<User> createProject(@Valid @RequestBody ProjectRequest projectRequest) {
        Optional<Project> projectOptional= projectRepository.findByLibelle(projectRequest.getLibelle());
        if(projectOptional.isPresent()){
            throw  new ProjectAlreadyExistException("Un projet avec ce nom existe déja!!!");
        }
        else {
            ProjectDTO projectDTO = projectService.saveProject(projectRequest);
            return   new ApiResponse<>(HttpStatus.OK.value(), "Projet Créé avec succées crée avec succés",projectDTO);
        }
    }

}
