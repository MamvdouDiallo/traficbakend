package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    Page<Document> findByProjectId(Long projectId, Pageable pageable);

}
