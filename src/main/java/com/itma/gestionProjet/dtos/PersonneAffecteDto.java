package com.itma.gestionProjet.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class PersonneAffecteDto {
    private Long id;
    private String idPap;
    private String idParcelle;
    private Integer nombreParcelle;
    private String categorie;
    private String prenom;
    private String nom;
    private Date dateNaissance;
    private String lieuNaissance;
    private String sexe;
    private String pays;
    private Integer age;
    private String nationalite;
    private String departement;
    private String typeIdentification;
    private String numeroIdentification;
    private String numeroTelephone;
    private String localiteResidence;
    private String region;
    private String prenomMere;
    private String prenomPere;
    private String statutPap;
    private String statutVulnerable;
    private String situationMatrimoniale;
    private String prenomExploitant;
    private String nomExploitant;
    private Double superficieAffecte;
    private String typeCulture;
    private String typeEquipement;
    private Double superficieCultive;
    private String descriptionBienAffecte;
    private Long projectId;
    private String signaturePath;
}
