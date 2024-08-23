package com.itma.gestionProjet.dtos;


import lombok.Data;

@Data
public class ArticleEntenteDto {
    private Long id;
    private  String titre;
    private String description;
    private Long ententeCompensationId;

}