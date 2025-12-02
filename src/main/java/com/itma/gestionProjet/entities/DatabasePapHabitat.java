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
    private String nom;
    private String prenom;
    private String surnom;
    private String sexe;
    private Long age;
    private LocalDate dateNaissance;


    private String adresseHabitat;
    private String typeHabitat; // ex: maison, appartement, squatt
    private String statutOccupation; // locataire, proprietaire, heberge
    private Double superficieHabitat;


    private Double perteBatiment;
    private Double perteMobiliers;
    private Double perteCloture;
    private Double fraisDeplacement;
    private Double perteTotale;


    private String vulnerabilite;


    private String situationMatrimoniale;
    private String niveauEtude;
    private String activitePrincipale;
    private String membreFoyer;
    private String membreFoyerHandicape;
    private String roleDansFoyer;


    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;


    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'PAPHABITAT'")
    private String type = "PAPHABITAT";


    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String description;
}