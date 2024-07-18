package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.PartieInteresse;
import com.itma.gestionProjet.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PartieInteresseRepository extends JpaRepository<PartieInteresse, Long> {

    Optional<PartieInteresse> findByLibelle(String libelle);

    @Query("SELECT pi FROM PartieInteresse pi JOIN pi.categoriePartieInteresse c WHERE c.libelle = :libelle")
    Page<PartieInteresse> findByCategoriePartieInteresseLibelle(@Param("libelle") String libelle, Pageable pageable);
}
