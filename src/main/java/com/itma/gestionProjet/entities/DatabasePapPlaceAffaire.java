package com.itma.gestionProjet.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;


@Entity
@Data
public class DatabasePapPlaceAffaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codePap;
    @Column(columnDefinition = "LONGTEXT")
    private String caracteristiquePlaceAffaire;
    private String nom;
    private String prenom;
    private String codePlaceAffaire;
    private String commune;
    private String departement;
    private String nombrePlaceAffaire;
    private String surnom;
    private String sexe;
    private Long age;
    private LocalDate dateNaissance; // Ajoutez ce champ
    private String roleDansFoyer; // "chef de ménage", "membre", etc.
    private String existePni;
    private String typePni;
    private String numeroPni;
    private String numeroTelephone;
    private String photoPap;
    private String photoPlaceAffaire;
    @Column(columnDefinition = "LONGTEXT")
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
    @Column(columnDefinition = "LONGTEXT")
    private String informationsEtendues;
    @Column(columnDefinition = "LONGTEXT")
    private String evaluationPerte;
    private String statutPap;
    private String vulnerabilite;
    private String pj1;
    private String pj2;
    private String pj3;
    private String pj4;
    private String pj5;
    @Column(columnDefinition = "LONGTEXT")
    private String infosComplemenataires;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'PAPPLACEAFFAIRE'")
    private String type = "PAPPLACEAFFAIRE";


    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private  String perteArbreJeune;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private  String perteArbreAdulte;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private  String perteEquipement;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private  String perteRevenue;
    private Double appuieRelocalisation;
    private Double perteTotale;
    private Double fraisDeplacement;


    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private  String perteBatiment;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private  String perteLoyer;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private  String perteCloture;

}
