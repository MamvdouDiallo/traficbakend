package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.Fichier;
import com.itma.gestionProjet.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FichierRepository  extends JpaRepository<Fichier,Long> {
    List<Fichier> findByProject(Project project);
}
