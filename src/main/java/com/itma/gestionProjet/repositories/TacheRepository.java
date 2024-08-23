package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.Tache;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TacheRepository extends JpaRepository<Tache, Long> {

    @Query("SELECT t FROM Tache t JOIN t.utilisateurs u WHERE u.id = :userId")
    Page<Tache> findTachesByUserId(@Param("userId") Long userId, Pageable pageable);
}
