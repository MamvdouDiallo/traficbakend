package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.Betails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BetailsRepository extends JpaRepository<Betails, Long> {
    List<Betails> findByCodePap(String codePap);

}
