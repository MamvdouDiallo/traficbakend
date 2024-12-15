package com.itma.gestionProjet.services;

import com.itma.gestionProjet.dtos.DatabasePapPlaceAffaireRequestDTO;
import com.itma.gestionProjet.dtos.DatabasePapPlaceAffaireResponseDTO;

import java.util.List;

public interface DatabasePapPlaceAffaireService {
    void createDatabasePapPlaceAffaire(List<DatabasePapPlaceAffaireRequestDTO> requestDTOs);
    List<DatabasePapPlaceAffaireResponseDTO> getAllDatabasePapPlaceAffaire(int page, int size);
    DatabasePapPlaceAffaireResponseDTO getDatabasePapPlaceAffaireById(Long id);
    void updateDatabasePapPlaceAffaire(Long id, DatabasePapPlaceAffaireRequestDTO requestDTO);
    void deleteDatabasePapPlaceAffaire(Long id);

    long getTotalCount();
}
