package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.dtos.PlainteDto;
import com.itma.gestionProjet.entities.EntenteCompensationPap;
import com.itma.gestionProjet.entities.Plainte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlainteRepository extends JpaRepository<Plainte, Long> {

    Optional<List<Plainte>> findByCodePap(String codePap);

    Page<Plainte> findByProjectId(Long projectId, Pageable pageable);
    Page<Plainte> findByCodePap(String codePap, Pageable pageable);


    @Query("SELECT p FROM Plainte p WHERE " +
            "(:projectId IS NULL OR p.project.id = :projectId) AND " +
            "LOWER(p.nom) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.prenom) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.codePap) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.numeroDossier) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.typeIdentification) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.numeroIdentification) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.contact) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.natureBienAffecte) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.etat) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.descriptionObjet) LIKE LOWER(CONCAT('%', :searchTerm, '%')) "
    )
    Page<Plainte> searchGlobal(@Param("searchTerm") String searchTerm, @Param("projectId") Optional<Long> projectId, Pageable pageable);

}
