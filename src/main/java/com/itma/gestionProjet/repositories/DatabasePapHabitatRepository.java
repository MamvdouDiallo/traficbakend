package com.itma.gestionProjet.repositories;


import com.itma.gestionProjet.entities.DatabasePapHabitat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;


public interface DatabasePapHabitatRepository extends JpaRepository<DatabasePapHabitat, Long> {
    Page<DatabasePapHabitat> findByProjectId(Long projectId, Pageable pageable);
    long countByProjectId(Long projectId);


    @Query("""
SELECT p FROM DatabasePapHabitat p
WHERE (:projectId IS NULL OR p.project.id = :projectId)
AND (
LOWER(p.nom) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
LOWER(p.prenom) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
LOWER(p.codePap) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
LOWER(p.adresseHabitat) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
)
""")
    Page<DatabasePapHabitat> searchGlobal(@Param("searchTerm") String searchTerm, @Param("projectId") Long projectId, Pageable pageable);

    @Query("SELECT COUNT(d) FROM DatabasePapHabitat d WHERE " +
            "(:projectId IS NULL OR d.project.id = :projectId) AND (" +
            "LOWER(d.nom) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.prenom) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.codePap) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(d.typeHabitat) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            ")")
    long countBySearchTermAndProjectId(
            @Param("searchTerm") String searchTerm,
            @Param("projectId") Long projectId
    );





    Optional<DatabasePapHabitat> findByCodePap(String codePap);


    long countByIdIn(List<Long> ids);
    void deleteAllByIdIn(List<Long> ids);
    void deleteAllByProjectId(Long projectId);
}