package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.DatabasePapAgricole;
import com.itma.gestionProjet.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatabasePapAgricoleRepository  extends JpaRepository<DatabasePapAgricole, Long> {
}
