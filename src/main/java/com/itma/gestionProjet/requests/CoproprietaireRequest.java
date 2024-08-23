package com.itma.gestionProjet.requests;

import lombok.Data;

@Data
public class CoproprietaireRequest {
    private String prenomNom;
    private String contactTelephonique;
    private String codePap;
    private String codeCoProprietaire;
    private String sexe;
    private int age;
    private String situationMatrimoniale;
    private String photo;
    private String infoComplementaire;

}
