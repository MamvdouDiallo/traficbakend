package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.Rencontre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RencontreRepository extends JpaRepository<Rencontre, Long> {

    Page<Rencontre> findByProjectId(Long projectId, Pageable pageable);
}
