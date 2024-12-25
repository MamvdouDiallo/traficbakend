package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.DatabasePapPlaceAffaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DatabasePapPlaceAffaireRepository extends JpaRepository<DatabasePapPlaceAffaire, Long> {
    Optional<DatabasePapPlaceAffaire> findByCodePap(String codePap);
}

