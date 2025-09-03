package com.itma.gestionProjet.repositories;


import com.itma.gestionProjet.entities.Entente;
import com.itma.gestionProjet.entities.StatutEntente;
import com.itma.gestionProjet.entities.TypePap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntenteRepository extends JpaRepository<Entente, Long> {
//    List<Entente> findByProjectId(Long projectId);
   List<Entente> findByPapIdAndPapType(Long papId, TypePap papType);
    List<Entente> findByStatut(StatutEntente statut);
    List<Entente> findByEtatProcessus(String etatProcessus);

    Page<Entente> findByProjectId(Long projectId, Pageable pageable);
    Page<Entente> findAll(Pageable pageable);
    Optional<Entente> findByCodePap(String codePap);



}