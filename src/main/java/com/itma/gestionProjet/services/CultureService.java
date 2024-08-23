package com.itma.gestionProjet.services;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.dtos.CultureDTO;
import com.itma.gestionProjet.entities.Culture;
import com.itma.gestionProjet.requests.CultureRequest;

import java.util.List;

public interface CultureService {
    List<CultureDTO> createCulture(List<CultureRequest> culture);
    AApiResponse<Culture> getAllCultures(int page, int size);
    AApiResponse<Culture> getCultureById(Long id);
    AApiResponse<Culture> updateCulture(Long id, Culture culture);
    AApiResponse<Culture> deleteCulture(Long id);

    AApiResponse<List<Culture>> getCulturesByCodePap(String codePap, int offset, int max);
}