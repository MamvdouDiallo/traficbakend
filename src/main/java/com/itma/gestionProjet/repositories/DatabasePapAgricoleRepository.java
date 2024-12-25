package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.DatabasePapAgricole;
import com.itma.gestionProjet.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DatabasePapAgricoleRepository  extends JpaRepository<DatabasePapAgricole, Long> {

    Optional<DatabasePapAgricole> findByCodePap(String codePap);

}
