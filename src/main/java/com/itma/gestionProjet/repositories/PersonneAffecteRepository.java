package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.PersonneAffecte;
import com.itma.gestionProjet.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonneAffecteRepository extends JpaRepository<PersonneAffecte, Long> {

    boolean existsByNumeroIdentification(String numeroIdentification);
   Optional<PersonneAffecte> findByNumeroIdentification(String numeroIdentification);

    Optional<Object> findByCodePap(String codePap);
}