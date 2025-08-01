package com.itma.gestionProjet.dtos;


import lombok.Data;

import java.time.LocalDate;

@Data
public class DatabasePapPlaceAffaireResponseDTO {
    private Long id;
    private Long age;
    private String codePap;
    private String caracteristiquePlaceAffaire;
    private String nom;
    private String prenom;
    private String codePlaceAffaire;
    private String commune;
    private String departement;
    private String nombrePlaceAffaire;
    private String surnom;
    private String sexe;
    private String existePni;
    private String typePni;
    private String numeroPni;
    private String numeroTelephone;
    private String photoPap;
    private String photoPlaceAffaire;
    private String pointGeometriques;
    private String nationalite;
    private String ethnie;
    private String langueParlee;
    private String situationMatrimoniale;
    private String niveauEtude;
    private String religion;
    private String activitePrincipale;
    private String membreFoyer;
    private String membreFoyerHandicape;
    private String informationsEtendues;
    private String evaluationPerte;
    private String statutPap;
    private String vulnerabilite;
    private String pj1;
    private String pj2;
    private String pj3;
    private String pj4;
    private String pj5;
    private String infosComplemenataires;
    private Long projectId;
    private  String type;

    private  Double perteArbreJeune;
    private  Double perteArbreAdulte;
    private  Double perteEquipement;
    private  Double perteRevenue;
    private Double appuieRelocalisation;
    private Double perteTotale;
    private Double fraisDeplacement;
    private  Double perteBatiment;
    private  Double perteLoyer;
    private  Double perteCloture;

    private LocalDate dateNaissance; // Ajoutez ce champ
    private String roleDansFoyer; // "chef de ménage", "membre", etc.

}
