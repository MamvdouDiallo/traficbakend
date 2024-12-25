package com.itma.gestionProjet.services;

import com.itma.gestionProjet.dtos.DatabasePapAgricoleRequestDTO;
import com.itma.gestionProjet.dtos.DatabasePapAgricoleResponseDTO;
import com.itma.gestionProjet.entities.DatabasePapAgricole;

import java.util.List;

public interface DatabasePapAgricoleService {
    List<DatabasePapAgricoleResponseDTO> getAllDatabasePapAgricole(int page, int size);
    DatabasePapAgricole getDatabasePapAgricoleById(Long id);
    void createDatabasePapAgricole(List<DatabasePapAgricoleRequestDTO> requestDTOs);
    void updateDatabasePapAgricole(Long id, DatabasePapAgricoleRequestDTO requestDTO);
    void deleteDatabasePapAgricole(Long id);

     long getTotalCount();

    DatabasePapAgricole getByCodePap(String codePap);
}

