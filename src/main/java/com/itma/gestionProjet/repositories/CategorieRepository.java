package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    boolean existsByLibelle(String libelle);

    Categorie findByLibelle(String s);

}
