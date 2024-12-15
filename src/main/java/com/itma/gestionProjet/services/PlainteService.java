package com.itma.gestionProjet.services;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.dtos.PlainteDto;
import com.itma.gestionProjet.requests.PlainteRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface PlainteService {
    PlainteDto createPlainte(PlainteRequest plainteRequest);
    Page<PlainteDto> getAllPlaintes(Pageable pageable);
    PlainteDto getPlainteById(Long id);
    PlainteDto updatePlainte(Long id, PlainteRequest plainteRequest);
    void deletePlainte(Long id);

    List<PlainteDto> createPlaintes(List<PlainteRequest> plainteRequests);

   // Map<String, Object> createPlaintes(List<PlainteRequest> plainteRequests);

  //  Map<String, Object> createPlaintes(List<PlainteRequest> plainteRequests);
    AApiResponse<PlainteDto> getPlainteByCodePap(String codePap, int page, int size);
}
