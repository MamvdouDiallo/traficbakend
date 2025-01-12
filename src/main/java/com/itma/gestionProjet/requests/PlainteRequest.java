package com.itma.gestionProjet.requests;

import lombok.Data;

import java.util.Date;
import java.util.List;


import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class PlainteRequest {

    private String numeroDossier;
    private  String libelleProjet;
    private String lieuEnregistrement;
    private Date dateEnregistrement;
    private Boolean isRecensed;
    private Boolean isSignedFileRecensement;
    private Date dateRecensement;
    private String natureBienAffecte;
    private String emplacementBienAffecte;
    private String typeIdentification;
    private String numeroIdentification;
    private Long projectId;
    private String contact;
    private String prenom;
    private String nom;
    private String codePap;
    private String vulnerabilite;
    private String email;
    private String situationMatrimoniale;
    private String descriptionObjet;
    private Boolean hasDocument;
    private String recommandation;
    private String etat;

    private List<String> documentUrls;

    private String urlSignaturePap;
    private String urlSignatureResponsable;
}
