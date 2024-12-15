package com.itma.gestionProjet.services;

import com.itma.gestionProjet.dtos.RencontreDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RencontreService {

    // Récupérer toutes les rencontres d'un projet avec pagination
    Page<RencontreDTO> getRencontresByProjectId(Long projectId, Pageable pageable);

    // Créer une nouvelle rencontre
    RencontreDTO createRencontre(RencontreDTO rencontreDTO);

    // Mettre à jour une rencontre existante
    RencontreDTO updateRencontre(Long id, RencontreDTO rencontreDTO);

    // Supprimer une rencontre
    void deleteRencontre(Long id);
}
