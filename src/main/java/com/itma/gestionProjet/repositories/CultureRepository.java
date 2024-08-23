package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.Culture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CultureRepository extends JpaRepository<Culture, Long> {

    List<Culture> findByCodePap(String codePap);
}