package com.itma.gestionProjet.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "audit_entries")
public class AuditEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String typeAction;
    private String details;
    private String utilisateur;
    private LocalDateTime dateAction;

    public AuditEntry() {}

    public AuditEntry(String typeAction, String details, String utilisateur, LocalDateTime dateAction) {
        this.typeAction = typeAction;
        this.details = details;
        this.utilisateur = utilisateur;
        this.dateAction = dateAction;
    }
}
