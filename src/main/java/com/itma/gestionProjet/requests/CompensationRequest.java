package com.itma.gestionProjet.requests;

import lombok.Data;

import java.util.Date;

@Data
public class CompensationRequest {
    private String codePap;
    private String categoriePap;
    private String prenom;
    private String nom;
    private String sexe;
    private String departement;
    private String commune;
    private String nationalite;
    private String typeIdentification;
    private String numeroIdentification;
    private String urlSignaturePap;
    private String urlSignatureResponsable;

    private Integer fraisDeplacement;
    private Integer appuiRelocalisation;

    private Integer nbArbreJeune;
    private Double prixArbreJeune;
    private Double perteArbreJeune;

    private Integer nbArbreAdulte;
    private Double prixArbreAdulte;
    private Double perteArbreAdulte;

    private Integer nbArbreEquipement;
    private Double prixArbreEquipement;
    private Double perteEquipement;

    private String categorieRevenue;
    private Double baremeRevenue;
    private Double perteRevenue;

    private Double fraisTotalDeplacement;
    private Double superficieAffecte;
    private Double baremeTypeSol;
    private Double perteTerre;
    private Double rendement;

    private Double superficieCultive;
    private Double prixMettreCarre;
    private Double perteRecolte;
    private Double perteTotale;

    private Date dateEnregistrement;

    private Long projectId; // Référence au projet
}
