package com.itma.gestionProjet.requests;


import lombok.Data;

@Data
public class GeoPolyRequest {
    private String geometrie;
    private String uid;
    private String axe;
    private String commune;
    private String codeRevise;
    private String codePap;
    private String prenomNom;
    private String localisation;
    private String zoneRegroupement;
    private String pkZoneRegroupement;
    private String typeImpact;
    private String infoComplementaire;

}
