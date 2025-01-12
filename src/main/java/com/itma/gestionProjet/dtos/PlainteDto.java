package com.itma.gestionProjet.dtos;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PlainteDto {

    private Long id;
    private String numeroDossier;
    private String lieuEnregistrement;
    private Date dateEnregistrement;
    private  String libelleProjet;
    private Boolean isRecensed;
    private Boolean isSignedFileRecensement;  // Correspond à la classe Entity
    private Date dateRecensement;  // Correspond à la classe Entity
    private String natureBienAffecte;  // Correspond à la classe Entity
    private String emplacementBienAffecte;  // Correspond à la classe Entity
    private String typeIdentification;
    private String numeroIdentification;
    private Long projectId;  // Correspond à l'identifiant du projet
    private String contact;  // Correspond à la classe Entity
    private String prenom;
    private String nom;
    private String codePap;
    private String vulnerabilite;
    private String email;  // Correspond à la classe Entity
    private String situationMatrimoniale;
    private String descriptionObjet;
    private Boolean hasDocument;  // Correspond à la classe Entity
    private String recommandation;
    private String etat;

    private List<String> documentUrls;  // Liste des URL des documents associés

    private String urlSignaturePap;  // Correspond à la classe Entity
    private String urlSignatureResponsable;  // Correspond à la classe Entity
}
