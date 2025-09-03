package com.itma.gestionProjet.repositories;


import com.itma.gestionProjet.entities.AuditEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends JpaRepository<AuditEntry, Long> {
}