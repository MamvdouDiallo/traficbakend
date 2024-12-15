package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.dtos.DatabasePapAgricoleRequestDTO;
import com.itma.gestionProjet.dtos.DatabasePapAgricoleResponseDTO;
import com.itma.gestionProjet.entities.DatabasePapAgricole;
import com.itma.gestionProjet.entities.Project;
import com.itma.gestionProjet.repositories.DatabasePapAgricoleRepository;
import com.itma.gestionProjet.repositories.ProjectRepository;
import com.itma.gestionProjet.services.DatabasePapAgricoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DatabasePapAgricoleServiceImpl implements DatabasePapAgricoleService {

    @Autowired
    private DatabasePapAgricoleRepository repository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<DatabasePapAgricoleResponseDTO> getAllDatabasePapAgricole(int page, int size) {
        var pageRequest = PageRequest.of(page, size);
        var pageResult = repository.findAll(pageRequest);

        List<DatabasePapAgricoleResponseDTO> data = pageResult.getContent().stream()
                .map(this::convertEntityToResponseDTO)
                .collect(Collectors.toList());

        return data;
    }



    @Override
    public DatabasePapAgricole getDatabasePapAgricoleById(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entity not found with ID: " + id));
        return entity;
    }


    @Override
    public void createDatabasePapAgricole(List<DatabasePapAgricoleRequestDTO> requestDTOs) {
        List<DatabasePapAgricole> entities = requestDTOs.stream().map(dto -> {
            DatabasePapAgricole entity = modelMapper.map(dto, DatabasePapAgricole.class);
            if (entity.getType() == null || entity.getType().isEmpty()) {
                entity.setType("PAPAGRICOLE");
            }
            if (dto.getProjectId() != null) {
                Project project = projectRepository.findById(dto.getProjectId())
                        .orElseThrow(() -> new RuntimeException("Project not found with ID: " + dto.getProjectId()));
                entity.setProject(project);
            }
            return entity;
        }).collect(Collectors.toList());
        repository.saveAll(entities);
    }

    @Override
    public void updateDatabasePapAgricole(Long id, DatabasePapAgricoleRequestDTO requestDTO) {
        DatabasePapAgricole entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entity not found with ID: " + id));

        modelMapper.typeMap(DatabasePapAgricoleRequestDTO.class, DatabasePapAgricole.class)
                .addMappings(mapper -> mapper.skip(DatabasePapAgricole::setId));

        modelMapper.map(requestDTO, entity);
        entity.setType("PAPAGRICOLE");
        if (requestDTO.getProjectId() != null) {
            Project project = projectRepository.findById(requestDTO.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project not found with ID: " + requestDTO.getProjectId()));
            entity.setProject(project);
        }

        repository.save(entity);
    }

    @Override
    public void deleteDatabasePapAgricole(Long id) {
        DatabasePapAgricole entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entity not found with ID: " + id));
        repository.delete(entity);
    }


    private DatabasePapAgricoleResponseDTO convertEntityToResponseDTO(DatabasePapAgricole entity) {
        DatabasePapAgricoleResponseDTO dto = modelMapper.map(entity, DatabasePapAgricoleResponseDTO.class);
        if (entity.getProject() != null) {
            dto.setProjectId(String.valueOf(entity.getProject().getId()));
        }
        return dto;
    }

    public long getTotalCount() {
        return repository.count();
    }

}