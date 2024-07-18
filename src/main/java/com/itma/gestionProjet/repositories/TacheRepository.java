package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.Tache;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TacheRepository extends JpaRepository<Tache, Long> {
}
