package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.DatabasePapEconomique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatabasePapEconomiqueRepository extends JpaRepository<DatabasePapEconomique, Long> {
}
