package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.dtos.CompensationDTO;
import com.itma.gestionProjet.entities.Compensation;
import com.itma.gestionProjet.entities.Project;
import com.itma.gestionProjet.repositories.CompensationRepository;
import com.itma.gestionProjet.repositories.ProjectRepository;
import com.itma.gestionProjet.requests.CompensationRequest;
import com.itma.gestionProjet.services.CompensationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompensationServiceImpl implements CompensationService {

    @Autowired
    private CompensationRepository repository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CompensationDTO createCompensation(CompensationRequest request) {
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Projet introuvable avec l'ID " + request.getProjectId()));

        Compensation compensation = modelMapper.map(request, Compensation.class);
        compensation.setProject(project);

        Compensation saved = repository.save(compensation);
        return modelMapper.map(saved, CompensationDTO.class);
    }

    @Override
    public Page<CompensationDTO> getAllCompensations(Pageable pageable) {
        Page<Compensation> compensations = repository.findAll(pageable);
        return compensations.map(compensation -> modelMapper.map(compensation, CompensationDTO.class));
    }

    @Override
    public CompensationDTO getCompensationById(Long id) {
        Compensation compensation = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compensation introuvable avec l'ID " + id));
        return modelMapper.map(compensation, CompensationDTO.class);
    }

    @Override
    public CompensationDTO updateCompensation(Long id, CompensationRequest request) {
        Compensation compensation = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compensation introuvable avec l'ID " + id));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Projet introuvable avec l'ID " + request.getProjectId()));

        modelMapper.map(request, compensation);
        compensation.setProject(project);

        Compensation updated = repository.save(compensation);
        return modelMapper.map(updated, CompensationDTO.class);
    }

    @Override
    public void deleteCompensation(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compensation introuvable avec l'ID " + id));
        repository.deleteById(id);
    }
}
