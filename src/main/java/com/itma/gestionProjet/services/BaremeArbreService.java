package com.itma.gestionProjet.services;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.dtos.BaremeArbreDTO;
import com.itma.gestionProjet.requests.BaremeArbreRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BaremeArbreService {


    AApiResponse<BaremeArbreDTO> createBaremeArbres(List<BaremeArbreRequest> requests);

    Page<BaremeArbreDTO> getAllBaremeArbre(Pageable pageable);

    BaremeArbreDTO getBaremeArbreById(Long id);

    BaremeArbreDTO updateBaremeArbre(Long id, BaremeArbreRequest request);

    void deleteBaremeArbre(Long id);
}
