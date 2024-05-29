package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.Project;
import com.itma.gestionProjet.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository  extends JpaRepository<Project,Long> {


    Optional<Project> findByLibelle(String name);
}
