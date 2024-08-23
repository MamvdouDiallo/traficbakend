package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.EmployePap;
import com.itma.gestionProjet.entities.GeoPoly;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeoPolyRepository extends JpaRepository<GeoPoly, Long> {

    List<GeoPoly> findByCodePap(String codePap);
}
