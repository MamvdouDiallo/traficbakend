package com.itma.gestionProjet.services;

import com.itma.gestionProjet.dtos.BaremeEquipementDTO;
import com.itma.gestionProjet.requests.BaremeEquipementRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BaremeEquipementService {
    List<BaremeEquipementDTO> createBaremeEquipements(List<BaremeEquipementRequest> requests);
    Page<BaremeEquipementDTO> getAllBaremeEquipements(Pageable pageable);
    BaremeEquipementDTO getBaremeEquipementById(Long id);
    BaremeEquipementDTO updateBaremeEquipement(Long id, BaremeEquipementRequest request);
    void deleteBaremeEquipement(Long id);
}

