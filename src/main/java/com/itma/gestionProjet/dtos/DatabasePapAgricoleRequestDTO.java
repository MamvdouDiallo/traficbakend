//package com.itma.gestionProjet.dtos;
//
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Lob;
//import lombok.Data;
//
//import java.time.LocalDate;
//
//@Data
//public class DatabasePapAgricoleRequestDTO {
//    private Long age;
//    private String codePap;
//    private String codeParcelle;
//    private String prenom;
//    private String nom;
//    private String caracteristiqueParcelle;
//    private String evaluationPerte;
//    private String commune;
//    private String departement;
//    private String nombreParcelle;
//    private String surnom;
//    private String sexe;
//    private String existePni;
//    private String typePni;
//    private String numeroPni;
//    private String numeroTelephone;
//    private String photoPap;
//    private String pointGeometriques;
//    private String superficie;
//    private String nationalite;
//    private String ethnie;
//    private String langueParlee;
//    private String situationMatrimoniale;
//    private String niveauEtude;
//    private String religion;
//    private String membreFoyer;
//    private String membreFoyerHandicape;
//    private String informationsEtendues;
//    private String statutPap;
//    private String vulnerabilite;
//    private String pj1;
//    private String pj2;
//    private String pj3;
//    private String pj4;
//    private String pj5;
//    private String infos_complemenataires;
//    private Long projectId;
//    private  Double perteTerre;
//    private  Double perteArbreJeune;
//    private  Double perteArbreAdulte;
//    private  Double perteEquipement;
//    private Double perteTotale;
//    private  Double perteBatiment;
//    private String activitePrincipale;
//    private LocalDate dateNaissance; // Ajoutez ce champ
//    private String roleDansFoyer; //
//    private  String optionPaiement;
//    private  Double perteTotaleArbre;
//    private String typeHandicape;
//    private  String description;
//    private  Double perteCloture;
//    private  Double perteRevenue;
//    private Double appuieRelocalisation;
//    private Double fraisDeplacement;
//
//    private  String vulne;
//
//    private  String  suptotale;
//
//    private  String  pourcaffecte;
//
//
//}


package com.itma.gestionProjet.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DatabasePapAgricoleRequestDTO {
    private String age; // Changé de Long à String
    private String codePap;
    private String codeParcelle;
    private String prenom;
    private String nom;
    private String caracteristiqueParcelle;
    private String evaluationPerte;
    private String commune;
    private String departement;
    private String nombreParcelle;
    private String surnom;
    private String sexe;
    private String existePni;
    private String typePni;
    private String numeroPni;
    private String numeroTelephone;
    private String photoPap;
    private String pointGeometriques;
    private String superficie;
    private String nationalite;
    private String ethnie;
    private String langueParlee;
    private String situationMatrimoniale;
    private String niveauEtude;
    private String religion;
    private String membreFoyer;
    private String membreFoyerHandicape;
    private String informationsEtendues;
    private String statutPap;
    private String vulnerabilite;
    private String pj1;
    private String pj2;
    private String pj3;
    private String pj4;
    private String pj5;
    private String infos_complemenataires;
    private Long projectId;
    private String perteTerre; // Changé de Double à String
    private String perteArbreJeune; // Changé de Double à String
    private String perteArbreAdulte; // Changé de Double à String
    private String perteEquipement; // Changé de Double à String
    private String perteTotale; // Changé de Double à String
    private String perteBatiment; // Changé de Double à String
    private String activitePrincipale;
    private LocalDate dateNaissance;
    private String roleDansFoyer;
    private String optionPaiement;
    private String perteTotaleArbre; // Changé de Double à String
    private String typeHandicape;
    private String description;
    private String perteCloture; // Changé de Double à String
    private String perteRevenue; // Changé de Double à String
    private String appuieRelocalisation; // Changé de Double à String
    private String fraisDeplacement; // Changé de Double à String
    private String vulne;
    private String suptotale;
    private String pourcaffecte;
}