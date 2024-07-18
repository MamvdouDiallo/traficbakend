package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.CategoriePartieInteresse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriePartieInteresseRepository extends JpaRepository   <CategoriePartieInteresse, Long> {
}
