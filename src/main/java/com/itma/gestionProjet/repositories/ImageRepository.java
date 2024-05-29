package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

}