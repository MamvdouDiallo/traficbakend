package com.itma.gestionProjet.services;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.dtos.PlainteDto;
import com.itma.gestionProjet.requests.PlainteRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlainteService {
    PlainteDto createPlainte(PlainteRequest plainteRequest);
    Page<PlainteDto> getAllPlaintes(Pageable pageable);
    PlainteDto getPlainteById(Long id);
    PlainteDto updatePlainte(Long id, PlainteRequest plainteRequest);
    void deletePlainte(Long id);


    AApiResponse<PlainteDto> getPlainteByCodePap(String codePap, int page, int size);
}
