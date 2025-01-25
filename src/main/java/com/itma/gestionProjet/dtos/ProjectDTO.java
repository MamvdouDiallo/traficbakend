package com.itma.gestionProjet.dtos;

import com.itma.gestionProjet.entities.Fichier;
import com.itma.gestionProjet.entities.File;
import com.itma.gestionProjet.entities.Image;
import com.itma.gestionProjet.entities.NormeProjet;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProjectDTO {
    private  String id;
    private  String libelle;
    private String description;
    private  String status;
    private Date datedebut;
    private  Date datefin;
    private  String imageUrl;
    private  List<MoDTO> users;
    private  List<NormeProjet> normes;
}
