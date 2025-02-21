package com.itma.gestionProjet.entities;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class DatabasePapAgricole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codePap;
    private String codeParcelle;
    private String prenom;
    private String nom;

    @Lob
    @Column(columnDefinition = "LONGTEXT")  // Spécifie le type LONGTEXT
    private String caracteristiqueParcelle;  // Valeur très large

    @Lob
    @Column(columnDefinition = "LONGTEXT")  // Spécifie le type LONGTEXT
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

    @Lob
    @Column(columnDefinition = "LONGTEXT")
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

    @Lob
    @Column(columnDefinition = "LONGTEXT")  // Spécifie le type LONGTEXT
    private String informationsEtendues;

    private String statutPap;
    private String vulnerabilite;

    private String pj1;
    private String pj2;
    private String pj3;
    private String pj4;
    private String pj5;

    @Lob
    @Column(columnDefinition = "LONGTEXT")  // Spécifie le type LONGTEXT
    private String infos_complemenataires;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'PAPAGRICOLE'")
    private String type = "PAPAGRICOLE";

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private  String perteTerre;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private  String perteArbreJeune;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private  String perteArbreAdulte;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private  String perteArbreEquipement;

    private Double perteTotale;
}

