package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
