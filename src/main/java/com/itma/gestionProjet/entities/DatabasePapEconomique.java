package com.itma.gestionProjet.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class DatabasePapEconomique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String nombreCoproprietaire;
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
    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'PAPECONOMIQUE'")
    private String type = "PAPECONOMIQUE";
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
