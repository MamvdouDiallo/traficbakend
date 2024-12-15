package com.itma.gestionProjet.dtos;

import lombok.Data;

@Data
public class DatabasePapEconomiqueDto {
    private Long id;
    private String prenom;
    private String nom;
    private String codePap;
    private String commune;
    private String quartier;
    private String departement;
    private String photoPlaceAffaire;
    private String sexe;
    private String existePni;
    private String typePni;
    private String numeroPni;
    private String numeroTelephone;
    private String photoPap;
    private String photoPni;
    private String nationalite;
    private String langueParlee;
    private String personneInterroge;
    private String situationMatrimoniale;
    private String adresseMail;
    private String statutStructure;
    private String statutJuridiqueStructure;
    private String niveauEtude;
    private String contactRepresentant;
    private String nomCompletRepresentant;
    private String nomEntreprise;
    private String nombreCoproprietatire;
    private String photoFicheConsentement;
    private String informationsEtendues;
    private String descriptionAnimeaux;
    private String descriptionBatiment;
    private String descriptionCloture;
    private String detailCoproprietaire;
    private String detailEmploye;
    private String photoEquipement;
    private String descriptionEquipement;
    private String evaluationPerte;
    private String infosComplementaires;
    private String pj1;
    private String pj2;
    private String pj3;
    private String pj4;
    private String pj5;
    private String statutPap;
    private String vulnerabilite;
    private String type;
    private Long projectId;
}
