package com.itma.gestionProjet.dtos;

import lombok.Data;

import java.util.Date;


@Data
public class RencontreDTO {

    private Long id;
    private Date date;
    private String libelle;
    private String urlPvRencontre;
    private Long projectId;
}
