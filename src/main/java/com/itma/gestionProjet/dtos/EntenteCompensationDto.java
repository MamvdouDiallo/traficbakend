package com.itma.gestionProjet.dtos;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class EntenteCompensationDto {
    private Long id;
    private String libelleProjet;
    private String codePap;
    private String prenom;
    private String nom;
    private Date dateNaissance;
    private String lieuNaissance;
    private String naturePiece;
    private String numeroPiece;
    private String localiteEnregistrement;
    private  String dateEnregistrement;
    private  String urlSignatureResponsable;
    private  String urlSignaturePap;
    private List<ArticleEntenteDto> articles;

}
