package com.itma.gestionProjet.dtos;


import lombok.Data;

import lombok.Data;

@Data
public class EntenteCompensationPapDto {
    private Long id;
    private String codePap;
    private String categoriePap;
    private String prenom;
    private String nom;
    private String sexe;
    private String departement;
    private String commune;
    private String nationalite;
    private String typePni;  // Correction pour correspondre à l'entité
    private String numeroPni;  // Correction pour correspondre à l'entité
    private String urlSignaturePap;
    private String urlSignatureResponsable;

    private Double fraisDeplacement;
    private Double appuiRelocalisation;
    private Double fraisTotalDeplacement;
    private Double superficieAffecte;
    private Double baremeTypeSol;
    private Double perteTotalTerre;
    private Double perteTotale;

    private String perteRecoltes; // Correspond maintenant au champ @Lob
    private String perteArbres; // Correspond maintenant au champ @Lob
    private String perteRevenues; // Correspond maintenant au champ @Lob
    private String perteEquipements; // Correspond maintenant au champ @Lob
}

