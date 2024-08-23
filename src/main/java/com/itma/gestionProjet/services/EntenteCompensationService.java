package com.itma.gestionProjet.services;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.dtos.EntenteCompensationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EntenteCompensationService {
    EntenteCompensationDto createEntenteCompensation(EntenteCompensationDto ententeCompensationDto);
    EntenteCompensationDto getEntenteCompensationById(Long id);
    List<EntenteCompensationDto> getAllEntenteCompensations();

    Page<EntenteCompensationDto> getAllEntenteCompensations(Pageable pageable);

    void deleteEntenteCompensation(Long id);

    AApiResponse<EntenteCompensationDto> getEntenteCompensationByCodePap(String codePap);
}
