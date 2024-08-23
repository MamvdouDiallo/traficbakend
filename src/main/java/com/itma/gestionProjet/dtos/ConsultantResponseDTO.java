package com.itma.gestionProjet.dtos;

import com.itma.gestionProjet.entities.Image;
import com.itma.gestionProjet.entities.Tache;
import lombok.Data;

import java.util.List;


@Data
public class ConsultantResponseDTO {
    private Long id;
    private String lastname;
    private String firstname;
    private String email;
    private String date_of_birth;
    private String place_of_birth;
    private  String contact;
    private  String locality;
    private Boolean enabled;
    private Image image;
 //   private  List<ProjectDTOUSER> projects;
    private  List<Tache> taches;
}
