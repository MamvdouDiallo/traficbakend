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
    private Boolean isRecensed;
    private String lieuNaissance;
    private String nom;
    private String prenom;
    private String numeroIdentification;
    private String placeOfBirth;
    private String recommandation;
    private String situationMatrimoniale;
    private String typeIdentification;
    private String vulnerabilite;
    private  String codePap;
    private  String etat;
    private Long projectId;
    private List<String> documentUrls;

}
