package com.itma.gestionProjet.services;

import com.itma.gestionProjet.dtos.CategorieDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategorieService {
    Page<CategorieDto> getAllCategories(Pageable pageable);
    CategorieDto createCategorie(CategorieDto categorieDto);
    CategorieDto updateCategorie(Long id, CategorieDto categorieDto);
    void deleteCategorie(Long id);
    CategorieDto getCategorieById(Long id);
}
