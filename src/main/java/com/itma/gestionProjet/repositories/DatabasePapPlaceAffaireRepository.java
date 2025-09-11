package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.DatabasePapAgricole;
import com.itma.gestionProjet.entities.DatabasePapPlaceAffaire;
import com.itma.gestionProjet.entities.Plainte;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DatabasePapPlaceAffaireRepository extends JpaRepository<DatabasePapPlaceAffaire, Long> {
    Optional<DatabasePapPlaceAffaire> findByCodePap(String codePap);

    Page<DatabasePapPlaceAffaire> findByProjectId(Long projectId, Pageable pageable);

    long countByProjectId(Long projectId);


    @Query("SELECT d FROM DatabasePapPlaceAffaire d WHERE " +
            "(:projectId IS NULL OR d.project.id = :projectId) AND " +
            "(LOWER(d.nom) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.prenom) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.codePlaceAffaire) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.commune) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.departement) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.surnom) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.sexe) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.typePni) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.numeroPni) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.numeroTelephone) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.codePap) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.nationalite) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.ethnie) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.langueParlee) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.situationMatrimoniale) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.niveauEtude) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.religion) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.activitePrincipale) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.statutPap) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.vulnerabilite) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<DatabasePapPlaceAffaire> searchGlobal(
            @Param("searchTerm") String searchTerm,
            @Param("projectId") Long projectId,
            Pageable pageable
    );

    @Query("SELECT COUNT(d) FROM DatabasePapPlaceAffaire d WHERE " +
            "(:projectId IS NULL OR d.project.id = :projectId) AND " +
            "(LOWER(d.nom) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.prenom) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.codePlaceAffaire) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.commune) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.departement) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.surnom) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.sexe) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.typePni) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.numeroPni) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.numeroTelephone) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.codePap) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.nationalite) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.ethnie) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.langueParlee) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.situationMatrimoniale) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.niveauEtude) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.religion) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.activitePrincipale) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.statutPap) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.vulnerabilite) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    long countBySearchTermAndProjectId(
            @Param("searchTerm") String searchTerm,
            @Param("projectId") Long projectId
    );


    // Vider tous les PAPs d'un projet
    @Modifying
    @Transactional
    @Query("DELETE FROM DatabasePapPlaceAffaire d WHERE d.project.id = :projectId")
    void deleteAllByProjectId(@Param("projectId") Long projectId);

    // Supprimer par une liste d'IDs
    @Modifying
    @Transactional
    @Query("DELETE FROM DatabasePapPlaceAffaire d WHERE d.id IN :ids")
    void deleteAllByIdIn(@Param("ids") List<Long> ids);

    int countByIdIn(List<Long> ids);

    // Compter les PAPs d'un projet (déjà existant, je le vois)
//    long countByProjectId(Long projectId);
}
