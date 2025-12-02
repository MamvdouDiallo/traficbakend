package com.itma.gestionProjet.dtos;
import lombok.Data;
import java.time.LocalDate;


@Data
public class DatabasePapHabitatResponseDTO {
    private Long id;
    private String codePap;
    private String nom;
    private String prenom;
    private String surnom;
    private String sexe;
    private Long age;
    private LocalDate dateNaissance;


    private String adresseHabitat;
    private String typeHabitat;
    private String statutOccupation;
    private Double superficieHabitat;


    private Double perteBatiment;
    private Double perteMobiliers;
    private Double perteCloture;
    private Double fraisDeplacement;
    private Double perteTotale;


    private String situationMatrimoniale;
    private String niveauEtude;
    private String activitePrincipale;
    private String membreFoyer;
    private String membreFoyerHandicape;
    private String roleDansFoyer;


    private Long projectId;
    private String description;
}
