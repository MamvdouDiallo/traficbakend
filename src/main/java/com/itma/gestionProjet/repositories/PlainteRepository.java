package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.dtos.PlainteDto;
import com.itma.gestionProjet.entities.Plainte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlainteRepository extends JpaRepository<Plainte, Long> {

    Optional<List<Plainte>> findByCodePap(String codePap);


    Page<Plainte> findByCodePap(String codePap, Pageable pageable);

}
