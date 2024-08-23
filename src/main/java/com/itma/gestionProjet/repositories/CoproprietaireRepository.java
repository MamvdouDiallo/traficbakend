package com.itma.gestionProjet.repositories;


import com.itma.gestionProjet.entities.Coproprietaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoproprietaireRepository extends JpaRepository<Coproprietaire, Long> {
    List<Coproprietaire> findByCodePap(String codePap);

}

