package com.itma.gestionProjet.requests;

import lombok.Data;

@Data
public class CultureRequest {
    private String codePap; // Code de la personne affectée pour retrouver l'entité
    private String typeCulture;
    private String description;
}
