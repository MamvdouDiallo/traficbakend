package com.itma.gestionProjet.services;

import com.itma.gestionProjet.dtos.CompensationDTO;
import com.itma.gestionProjet.requests.CompensationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CompensationService {
    CompensationDTO createCompensation(CompensationRequest request);
    Page<CompensationDTO> getAllCompensations(Pageable pageable);
    CompensationDTO getCompensationById(Long id);
    CompensationDTO updateCompensation(Long id, CompensationRequest request);
    void deleteCompensation(Long id);
}

