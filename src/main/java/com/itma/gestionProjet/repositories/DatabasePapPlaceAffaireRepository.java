package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.DatabasePapPlaceAffaire;
import com.itma.gestionProjet.entities.Plainte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DatabasePapPlaceAffaireRepository extends JpaRepository<DatabasePapPlaceAffaire, Long> {
    Optional<DatabasePapPlaceAffaire> findByCodePap(String codePap);

    Page<DatabasePapPlaceAffaire> findByProjectId(Long projectId, Pageable pageable);
}

