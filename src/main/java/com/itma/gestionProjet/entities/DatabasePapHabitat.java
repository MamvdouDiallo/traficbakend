package com.itma.gestionProjet.entities;


import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;


@Entity
@Data
public class DatabasePapHabitat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codePap;
    private String codeParcelle;
    private String prenom;
    private String nom;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String caracteristiqueParcelle;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
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
    private Long age ;
    private LocalDate dateNaissance; // Ajoutez ce champ
    private String roleDansFoyer; // "chef de ménage", "membre", etc.
    private String activitePrincipale;

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
    private String typeHandicape;

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

    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'PAPHABITAT'")
    private String type = "PAPHABITAT";

    //@Lob
    // @Column(columnDefinition = "LONGTEXT")
    private  Double perteTerre;

    //@Lob
    //@Column(columnDefinition = "LONGTEXT")
    private  Double perteArbreJeune;

    //@Lob
    // @Column(columnDefinition = "LONGTEXT")
    private  Double perteArbreAdulte;

    //@Lob
    // @Column(columnDefinition = "LONGTEXT")
    private  Double perteEquipement;

    private Double perteTotale;
    private  Double perteCloture;
    private Double fraisDeplacement;


    private  Double perteRevenue;
    private Double appuieRelocalisation;


    // @Lob
    //@Column(columnDefinition = "LONGTEXT")
    private  Double perteBatiment;

    private  String optionPaiement;
    private  Double perteTotaleArbre;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private  String description;

    private  String vulne;

    private  String  suptotale;

    private  String  pourcaffecte;


}