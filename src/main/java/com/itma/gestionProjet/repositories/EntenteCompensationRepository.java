package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.EntenteCompensation;
import com.itma.gestionProjet.entities.Plainte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EntenteCompensationRepository extends JpaRepository<EntenteCompensation, Long> {

    Optional<List<EntenteCompensation>> findByCodePap(String codePap);
}
