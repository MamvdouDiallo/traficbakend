package com.itma.gestionProjet.services;

import com.itma.gestionProjet.dtos.EntenteCompensationPapDto;
import com.itma.gestionProjet.requests.EntenteCompensationPapRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface EntenteCompensationPapService {
    Page<EntenteCompensationPapDto> getAllEntentes(Pageable pageable, Long projectId);

    EntenteCompensationPapDto createEntente(EntenteCompensationPapRequest request);
    EntenteCompensationPapDto updateEntente(Long id, EntenteCompensationPapRequest request);

    void deleteEntente(Long id);

    EntenteCompensationPapDto getEntenteByCodePap(String codePap);
}
