package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    boolean existsByLibelle(String libelle);
}
