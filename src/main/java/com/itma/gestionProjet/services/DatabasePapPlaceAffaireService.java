package com.itma.gestionProjet.services;

import com.itma.gestionProjet.dtos.DatabasePapAgricoleResponseDTO;
import com.itma.gestionProjet.dtos.DatabasePapPlaceAffaireRequestDTO;
import com.itma.gestionProjet.dtos.DatabasePapPlaceAffaireResponseDTO;
import com.itma.gestionProjet.entities.DatabasePapPlaceAffaire;

import java.util.List;
import java.util.Optional;

public interface DatabasePapPlaceAffaireService {
    void createDatabasePapPlaceAffaire(List<DatabasePapPlaceAffaireRequestDTO> requestDTOs);
    List<DatabasePapPlaceAffaireResponseDTO> getAllDatabasePapPlaceAffaire(int page, int size);

    List<DatabasePapPlaceAffaireResponseDTO> getDatabasePapPlaceAffaireByProjectId(Long projectId, int page, int size);
    DatabasePapPlaceAffaireResponseDTO getDatabasePapPlaceAffaireById(Long id);
    void updateDatabasePapPlaceAffaire(Long id, DatabasePapPlaceAffaireRequestDTO requestDTO);
    void deleteDatabasePapPlaceAffaire(Long id);

    long getTotalCount();

    DatabasePapPlaceAffaire getByCodePap(String codePap);

    long getTotalCountByProjectId(Long projectId);


    // Nouvelle méthode pour la recherche globale
    List<DatabasePapPlaceAffaireResponseDTO> searchGlobalDatabasePapPlaceAffaire(String searchTerm, Optional<Long> projectId,int page, int size);

    long getTotalCountForSearch(String searchTerm);
}
