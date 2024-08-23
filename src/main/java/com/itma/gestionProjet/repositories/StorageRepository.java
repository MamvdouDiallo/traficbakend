package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StorageRepository extends JpaRepository<Image,Long> {


    Optional<Image> findByName(String fileName);
}
