package com.itma.gestionProjet.dtos;

import lombok.Data;

@Data
public class CompensationDTO {
    private Long id;
    private String codePap;
    private String prenom;
    private String nom;
    private String departement;
    private Double perteTotale;
    private Long projectId; // Référence au projet
}
