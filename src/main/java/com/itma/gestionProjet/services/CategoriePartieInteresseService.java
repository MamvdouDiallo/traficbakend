package com.itma.gestionProjet.services;

import com.itma.gestionProjet.entities.CategoriePartieInteresse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoriePartieInteresseService {
    List<CategoriePartieInteresse> getAllCategories();
    CategoriePartieInteresse getCategorieById(Long id);
    CategoriePartieInteresse createCategorie(CategoriePartieInteresse categorie);
    CategoriePartieInteresse updateCategorie(Long id, CategoriePartieInteresse categorie);
    void deleteCategorie(Long id);

    Page<CategoriePartieInteresse> getCategories(Pageable pageable);
}
