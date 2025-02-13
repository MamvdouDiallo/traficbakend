package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.EntenteCompensationPap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntenteCompensationPapRepository extends JpaRepository<EntenteCompensationPap, Long> {
    Page<EntenteCompensationPap> findByProjectId(Long projectId, Pageable pageable);
}
