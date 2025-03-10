package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.DatabasePapAgricole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DatabasePapAgricoleRepository  extends JpaRepository<DatabasePapAgricole, Long> {

    Optional<DatabasePapAgricole> findByCodePap(String codePap);

    Page<DatabasePapAgricole> findByProjectId(Long projectId, Pageable pageable);
    long countByProjectId(Long projectId);

    @Query("SELECT d FROM DatabasePapAgricole d WHERE " +
            "(:projectId IS NULL OR d.project.id = :projectId) AND " +
            "(LOWER(d.nom) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.prenom) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.codePap) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.commune) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.departement) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.sexe) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.existePni) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.typePni) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.numeroPni) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.numeroTelephone) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.nationalite) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.ethnie) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.langueParlee) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.situationMatrimoniale) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.niveauEtude) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.statutPap) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.vulnerabilite) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<DatabasePapAgricole> searchGlobal(
            @Param("searchTerm") String searchTerm,
            @Param("projectId") Optional<Long> projectId,
            Pageable pageable
    );


    @Query("SELECT COUNT(d) FROM DatabasePapAgricole d WHERE " +
            "(:projectId IS NULL OR d.project.id = :projectId) AND " +
            "LOWER(d.nom) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.prenom) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.codePap) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.commune) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.departement) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.sexe) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.existePni) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.typePni) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.numeroPni) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.numeroTelephone) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.nationalite) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.ethnie) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.langueParlee) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.situationMatrimoniale) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.niveauEtude) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.statutPap) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.vulnerabilite) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    long countBySearchTerm(@Param("searchTerm") String searchTerm);

}
