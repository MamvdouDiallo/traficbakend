package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.dtos.ProjectDTO;
import com.itma.gestionProjet.dtos.UserDTO;
import com.itma.gestionProjet.entities.*;
import com.itma.gestionProjet.exceptions.UserNotFoundException;
import com.itma.gestionProjet.repositories.*;
import com.itma.gestionProjet.requests.ProjectRequest;
import com.itma.gestionProjet.services.IProjectService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService implements IProjectService {


    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private NormeProjectRepository normeProjectRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public Optional<Project> findProjectByName(String name) {
        return Optional.empty();
    }

    @Override
    public ProjectDTO saveProject(Project p) {
        // Vérifiez si la liste des utilisateurs n'est pas vide
        if (p.getUsers() != null && !p.getUsers().isEmpty()) {
            List<User> users = p.getUsers().stream()
                    .map(user -> userRepository.findById(user.getId())
                            .orElseThrow(() -> new IllegalArgumentException("User not found: " + user.getId())))
                    .collect(Collectors.toList());
            p.setUsers(users);

            for (User user : users) {
                user.getProjects().add(p);
            }
        }
        // Sauvegardez le projet
        Project savedProject = projectRepository.save(p);
        return convertEntityToDto(savedProject);
    }
    @Override
    public ProjectDTO updateProject(Project p) {

        Project existingProject = projectRepository.findById((long) Math.toIntExact(p.getId()))
                .orElseThrow(() -> new IllegalArgumentException("Project not found with id " + p.getId()));
        existingProject.setLibelle(p.getLibelle());
        existingProject.setDescription(p.getDescription());
        existingProject.setStatus(p.getStatus());
        existingProject.setCategorie(p.getCategorie());

        // Mise à jour de la date de début et de fin
        existingProject.setDatedebut(p.getDatedebut());
        existingProject.setDatefin(p.getDatefin());
        // Dissociation des utilisateurs actuels
       if (existingProject.getUsers()!= null) {
            for (User user : existingProject.getUsers()) {
                user.getProjects().remove(existingProject);
            }
        }



        // Mise à jour des utilisateurs associés
       /*
        if (p.getUsers()!= null &&!p.getUsers().isEmpty()) {
            List<User> users = p.getUsers().stream()
                    .map(user -> userRepository.findById(user.getId())
                            .orElseThrow(() -> new IllegalArgumentException("User not found: " + user.getId())))
                    .collect(Collectors.toList());
            existingProject.setUsers(users);

            for (User user : users) {
                user.getProjects().add(p);
            }
        }

        */
        // Sauvegarde des modifications
        return convertEntityToDto(projectRepository.save(existingProject));
    }

    @Override
    public Optional<ProjectDTO> findProjectById(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            return project.map(this::convertEntityToDto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAllByOrderByIdDesc().stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }
    @Override
    public void deleteProject(Project p) {

    }

    @Override
    public void deleteProjectById(Long id) {

        Project project = projectRepository.findById((long) Math.toIntExact(id)).orElseThrow(() -> new IllegalArgumentException("project not found with id " + id));
        // Remove the user's associations with roles
        if (project.getImage() != null) {
            Image image = project.getImage();
            project.setImage(null);
            imageRepository.delete(image);
        }

        List<NormeProjet> normeProjects = normeProjectRepository.findByProjectId(id);
        for (NormeProjet normeProject : normeProjects) {
            normeProjectRepository.delete(normeProject);
        }

        // Remove the associations with File
        List<File> files = fileRepository.findByProjectId(id);
        for (File file : files) {
            fileRepository.delete(file);
        }
        projectRepository.save(project);

        // Delete the user
        projectRepository.deleteById((long) Math.toIntExact(id));
    }

    @Override
    public ProjectDTO convertEntityToDto(Project p) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(p, ProjectDTO.class);
    }

    @Override
    public Project convertDtoToEntity(Project p) {
        Project project = new Project();
        project = modelMapper.map(p, Project.class);
        return project;
    }
}
