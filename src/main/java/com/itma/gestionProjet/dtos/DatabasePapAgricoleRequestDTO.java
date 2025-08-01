package com.itma.gestionProjet.dtos;


import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DatabasePapAgricoleRequestDTO {
    private Long age;
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
    private  Double perteTerre;
    private  Double perteArbreJeune;
    private  Double perteArbreAdulte;
    private  Double perteEquipement;
    private Double perteTotale;
    private  Double perteBatiment;
    private String activitePrincipale;
    private LocalDate dateNaissance; // Ajoutez ce champ
    private String roleDansFoyer; //
    private  String optionPaiement;
    private  Double perteTotaleArbre;
    private String typeHandicape;
    private  String description;
}
