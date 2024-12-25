package com.itma.gestionProjet.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Compensation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Temporal(TemporalType.DATE)
    private Date dateEnregistrement;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
}

