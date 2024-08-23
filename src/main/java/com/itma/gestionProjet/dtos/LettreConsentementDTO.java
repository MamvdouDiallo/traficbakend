package com.itma.gestionProjet.dtos;

import lombok.Data;

@Data
public class LettreConsentementDTO {
    private String contenu;
    private Long partie_affecte_id;

    // Getters and Setters
}
