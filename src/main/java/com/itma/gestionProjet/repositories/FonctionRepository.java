package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.Fonction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FonctionRepository extends JpaRepository<Fonction, Long> {
    boolean existsByLibelle(String libelle);
   Optional<Fonction> findByLibelle(String manager);
}

