package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.File;
import com.itma.gestionProjet.entities.NormeProjet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileRepository  extends JpaRepository<File,Long> {

    boolean existsByNameAndProject_Id(String name, Long projectId);

    void deleteByProjectId(Long projectId);


    @Query("SELECT np FROM File np WHERE np.project.id = :projectId")
    List<File> findByProjectId(@Param("projectId") Long projectId);
}
