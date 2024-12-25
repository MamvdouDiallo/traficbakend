package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.Compensation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompensationRepository extends JpaRepository<Compensation, Long> {
}
