package com.itma.gestionProjet.services;

import com.itma.gestionProjet.dtos.PartieInteresseDTO;
import com.itma.gestionProjet.entities.CategoriePartieInteresse;
import com.itma.gestionProjet.entities.PartieInteresse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PartieInteresseService {
    List<PartieInteresse> findAll();
    Optional<PartieInteresse> findById(Long id);
    PartieInteresse save(PartieInteresseDTO partieInteresse);
    void deleteById(Long id);

    Page<PartieInteresse> getPartieInteresses(Pageable pageable);

     Page<PartieInteresse> findByCategoriePartieInteresseLibelle(String libelle, Pageable pageable);
    PartieInteresse  update(Long id, PartieInteresseDTO partieInteresse);
}