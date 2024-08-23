package com.itma.gestionProjet.dtos;

import com.itma.gestionProjet.entities.PartieInteresse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartieInteresseDTO {
    private String adresse;
    private String adresseContactPrincipal;
    private Long categoriePartieInteresse;
    private String courielPrincipal;
    private String dateNaissanceContactPrincipal;
    private String emailContactPrincipal;
    private String libelle;
    private String lieuNaisasnceContactPrincipal;
    private String localisation;
    private String nomContactPrincipal;
    private String normes;
    private String prenomContactPrincipal;
    private String sexeContactPrincipal;
    private String statut;
    private String telephoneContactPrincipal;
    private  Long project_id;
}