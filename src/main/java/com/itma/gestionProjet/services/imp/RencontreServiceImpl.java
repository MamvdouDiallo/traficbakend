package com.itma.gestionProjet.services.imp;


import com.itma.gestionProjet.dtos.RencontreDTO;
import com.itma.gestionProjet.entities.Project;
import com.itma.gestionProjet.entities.Rencontre;
import com.itma.gestionProjet.repositories.ProjectRepository;
import com.itma.gestionProjet.repositories.RencontreRepository;
import com.itma.gestionProjet.services.RencontreService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RencontreServiceImpl implements RencontreService {


    @Autowired
    private RencontreRepository rencontreRepository;

    @Autowired
    private ProjectRepository projectRepository; // Pour vérifier l'existence du projet

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<RencontreDTO> getRencontresByProjectId(Long projectId, Pageable pageable) {
        if (!projectRepository.existsById(projectId)) {
            throw new RuntimeException("Project not found");
        }
        Page<Rencontre> rencontresPage = rencontreRepository.findByProjectId(projectId, pageable);
        return rencontresPage.map(rencontre -> modelMapper.map(rencontre, RencontreDTO.class));
    }

    @Override
    public Page<RencontreDTO> getAllRencontres(Pageable pageable) {
        Page<Rencontre> rencontresPage = rencontreRepository.findAll(pageable);
        return rencontresPage.map(rencontre -> modelMapper.map(rencontre, RencontreDTO.class));
    }
    @Override
    public RencontreDTO createRencontre(RencontreDTO rencontreDTO) {
        // Vérification si le projet existe
        if (!projectRepository.existsById(rencontreDTO.getProjectId())) {
            throw new RuntimeException("Project not found");
        }

        Rencontre rencontre = modelMapper.map(rencontreDTO, Rencontre.class);
        Project project = projectRepository.findById(rencontreDTO.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        rencontre.setProject(project);
        Rencontre savedRencontre = rencontreRepository.save(rencontre);
        return modelMapper.map(savedRencontre, RencontreDTO.class);
    }

    @Override
    public RencontreDTO updateRencontre(Long id, RencontreDTO rencontreDTO) {
        // Vérification si la rencontre existe
        Rencontre existingRencontre = rencontreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rencontre not found"));

        // Vérification si le projet existe
        if (!projectRepository.existsById(rencontreDTO.getProjectId())) {
            throw new RuntimeException("Project not found");
        }

        Project project = projectRepository.findById(rencontreDTO.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // Mise à jour des champs
        existingRencontre.setDate(rencontreDTO.getDate());
        existingRencontre.setLibelle(rencontreDTO.getLibelle());
        existingRencontre.setUrlPvRencontre(rencontreDTO.getUrlPvRencontre());
        existingRencontre.setProject(project);

        Rencontre updatedRencontre = rencontreRepository.save(existingRencontre);
        return modelMapper.map(updatedRencontre, RencontreDTO.class);
    }

    @Override
    public void deleteRencontre(Long id) {
        // Vérification si la rencontre existe
        Rencontre existingRencontre = rencontreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rencontre not found"));

        rencontreRepository.delete(existingRencontre);
    }
}
