package com.itma.gestionProjet.dtos;

import lombok.Data;

@Data
public class DatabasePapAgricoleResponseDTO {
    private Long id;
    private String codePap;
    private String codeParcelle;
    private String nombreParcelle;
    private String prenom;
    private String nom;
    private String caracteristiqueParcelle;
    private String evaluationPerte;
    private String commune;
    private String departement;
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
    private String projectId;
    private  String type;
}