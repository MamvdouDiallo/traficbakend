package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.Fonction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FonctionRepository extends JpaRepository<Fonction, Long> {
    boolean existsByLibelle(String libelle);
}

