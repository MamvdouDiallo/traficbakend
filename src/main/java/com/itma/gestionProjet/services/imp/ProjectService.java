package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.dtos.ProjectDTO;
import com.itma.gestionProjet.dtos.UserDTO;
import com.itma.gestionProjet.entities.Project;
import com.itma.gestionProjet.repositories.ProjectRepository;
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
    @Override
    public Optional<Project> findProjectByName(String name) {
        return Optional.empty();
    }

    @Override
    public ProjectDTO saveProject(ProjectRequest p) {
        return  convertEntityToDto(projectRepository.save(convertDtoToEntity(p)));
    }
    @Override
    public ProjectDTO updateProject(UserDTO p) {
        return null;
    }
    @Override
    public ProjectDTO getProject(Long id) {
        return null;
    }

    @Override
    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }
    @Override
    public void deleteProject(Project p) {

    }

    @Override
    public void deleteProjectById(Long id) {

    }

    @Override
    public ProjectDTO convertEntityToDto(Project p) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(p, ProjectDTO.class);
    }

    @Override
    public Project convertDtoToEntity(ProjectRequest ProjectDto) {
        Project project = new Project();
        project = modelMapper.map(ProjectDto, Project.class);
        return project;
    }
}
