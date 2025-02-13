package com.itma.gestionProjet.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class EntenteCompensationPap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codePap;
    private String categoriePap;
    private String prenom;
    private String nom;
    private String sexe;
    private String departement;
    private String commune;
    private String nationalite;
    private String typePni;
    private String numeroPni;
    private String urlSignaturePap;
    private String urlSignatureResponsable;

    private Double fraisDeplacement;
    private Double appuiRelocalisation;

    private Double fraisTotalDeplacement;
    private Double superficieAffecte;
    private Double baremeTypeSol;
    private Double perteTotalTerre;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String perteRecoltes;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String perteArbres;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String perteRevenues;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String perteEquipements;

    private Double perteTotale;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "database_pap_agricole_id", nullable = true)
    private DatabasePapAgricole databasePapAgricole;

    @ManyToOne
    @JoinColumn(name = "database_pap_place_affaire_id", nullable = true)
    private DatabasePapPlaceAffaire databasePapPlaceAffaire;
}
