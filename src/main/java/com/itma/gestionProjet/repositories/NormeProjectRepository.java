package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.NormeProjet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NormeProjectRepository  extends JpaRepository<NormeProjet, Long> {
    void deleteByProjectId(Long projectId);

    boolean existsByTitre(String titre);


    @Query("SELECT np FROM NormeProjet np WHERE np.project.id = :projectId")
    List<NormeProjet> findByProjectId(@Param("projectId") Long projectId);

}
