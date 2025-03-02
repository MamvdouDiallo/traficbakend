package com.itma.gestionProjet.services;

import com.itma.gestionProjet.dtos.DatabasePapAgricoleRequestDTO;
import com.itma.gestionProjet.dtos.DatabasePapAgricoleResponseDTO;
import com.itma.gestionProjet.dtos.DatabasePapPlaceAffaireResponseDTO;
import com.itma.gestionProjet.dtos.PlainteDto;
import com.itma.gestionProjet.entities.DatabasePapAgricole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DatabasePapAgricoleService {
    List<DatabasePapAgricoleResponseDTO> getAllDatabasePapAgricole(int page, int size);

    List<DatabasePapAgricoleResponseDTO> getDatabasePapAgricoleByProjectId(Long projectId, int page, int size);
    DatabasePapAgricole getDatabasePapAgricoleById(Long id);
    void createDatabasePapAgricole(List<DatabasePapAgricoleRequestDTO> requestDTOs);
    void updateDatabasePapAgricole(Long id, DatabasePapAgricoleRequestDTO requestDTO);
    void deleteDatabasePapAgricole(Long id);

     long getTotalCount();

    DatabasePapAgricole getByCodePap(String codePap);
}

