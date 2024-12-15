package com.itma.gestionProjet.services;

import com.itma.gestionProjet.dtos.FonctionDto;
import com.itma.gestionProjet.requests.CategorieRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface FonctionService {
    Page<FonctionDto> getAllFonctions(Pageable pageable);
    FonctionDto createFonction(CategorieRequest fonctionDto);
    FonctionDto updateFonction(Long id, CategorieRequest fonctionDto);
    void deleteFonction(Long id);
    FonctionDto getFonctionById(Long id);
}
