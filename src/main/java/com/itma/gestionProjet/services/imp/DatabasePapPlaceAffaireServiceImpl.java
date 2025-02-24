package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.dtos.DatabasePapAgricoleRequestDTO;
import com.itma.gestionProjet.dtos.DatabasePapAgricoleResponseDTO;
import com.itma.gestionProjet.dtos.DatabasePapPlaceAffaireRequestDTO;
import com.itma.gestionProjet.dtos.DatabasePapPlaceAffaireResponseDTO;
import com.itma.gestionProjet.entities.DatabasePapAgricole;
import com.itma.gestionProjet.entities.DatabasePapPlaceAffaire;
import com.itma.gestionProjet.entities.EntenteCompensationPap;
import com.itma.gestionProjet.entities.Project;
import com.itma.gestionProjet.repositories.DatabasePapPlaceAffaireRepository;
import com.itma.gestionProjet.repositories.ProjectRepository;
import com.itma.gestionProjet.requests.EntenteCompensationPapRequest;
import com.itma.gestionProjet.services.DatabasePapPlaceAffaireService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DatabasePapPlaceAffaireServiceImpl implements DatabasePapPlaceAffaireService {

    @Autowired
    private DatabasePapPlaceAffaireRepository repository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void createDatabasePapPlaceAffaire(List<DatabasePapPlaceAffaireRequestDTO> requestDTOs) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.typeMap(DatabasePapPlaceAffaireRequestDTO.class, DatabasePapPlaceAffaire.class)
                .addMappings(mapper -> mapper.skip(DatabasePapPlaceAffaire::setId));

        List<DatabasePapPlaceAffaire> entities = requestDTOs.stream().map(dto -> {
            DatabasePapPlaceAffaire entity = modelMapper.map(dto, DatabasePapPlaceAffaire.class);
            if (entity.getType() == null || entity.getType().isEmpty()) {
                entity.setType("PAPPLACEAFFAIRE");
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
    /*
    public List<DatabasePapPlaceAffaireResponseDTO> getAllDatabasePapPlaceAffaire(int page, int size) {
        var pageRequest = PageRequest.of(page, size);
        var pageResult = repository.findAll(pageRequest);

        return pageResult.getContent().stream()
                .map(this::convertEntityToResponseDTO)
                .collect(Collectors.toList());
    }


     */



    public List<DatabasePapPlaceAffaireResponseDTO> getAllDatabasePapPlaceAffaire(int page, int size) {
        var pageRequest = PageRequest.of(page, size);
        var pageResult = repository.findAll(pageRequest);
        List<DatabasePapPlaceAffaireResponseDTO> data = pageResult.getContent().stream()
                .map(this::convertEntityToResponseDTO)
                .collect(Collectors.toList());

        return data;
    }


    @Override
    public DatabasePapPlaceAffaireResponseDTO getDatabasePapPlaceAffaireById(Long id) {
        DatabasePapPlaceAffaire entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Entity not found"));
        return modelMapper.map(entity, DatabasePapPlaceAffaireResponseDTO.class);
    }

    @Override
    public void updateDatabasePapPlaceAffaire(Long id, DatabasePapPlaceAffaireRequestDTO requestDTO) {
        DatabasePapPlaceAffaire entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Entity not found"));
        modelMapper.typeMap(DatabasePapPlaceAffaireRequestDTO.class, DatabasePapPlaceAffaire.class)
                .addMappings(mapper -> mapper.skip(DatabasePapPlaceAffaire::setId));
        modelMapper.map(requestDTO, entity);
        entity.setType("PAPPLACEAFFAIRE");
        if (requestDTO.getProjectId() != null) {
            Project project = projectRepository.findById(requestDTO.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project not found with ID: " + requestDTO.getProjectId()));
            entity.setProject(project);
        }


        repository.save(entity);
    }

    public long getTotalCount() {
        return repository.count();
    }


    @Override
    public void deleteDatabasePapPlaceAffaire(Long id) {
        DatabasePapPlaceAffaire entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Entity not found"));
        repository.delete(entity);
    }

    private DatabasePapPlaceAffaireResponseDTO convertEntityToResponseDTO(DatabasePapPlaceAffaire entity) {
        DatabasePapPlaceAffaireResponseDTO dto = modelMapper.map(entity, DatabasePapPlaceAffaireResponseDTO.class);
        if (entity.getProject() != null) {
            dto.setProjectId((long) entity.getProject().getId());
        }
        return dto;

    }

    public DatabasePapPlaceAffaire getByCodePap(String codePap) {
        return repository.findByCodePap(codePap)
                .orElseThrow(() -> new EntityNotFoundException("DatabasePapPlaceAffaire avec codePap " + codePap + " introuvable"));
    }
}
