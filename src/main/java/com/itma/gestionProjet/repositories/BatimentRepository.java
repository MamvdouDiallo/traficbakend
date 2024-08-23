package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.Batiment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BatimentRepository extends JpaRepository<Batiment, Long> {
    List<Batiment> findByCodePap(String codePap);
}