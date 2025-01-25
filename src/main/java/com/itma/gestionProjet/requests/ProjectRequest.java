package com.itma.gestionProjet.requests;

import com.itma.gestionProjet.entities.*;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;



@Data
public class ProjectRequest {
    private Long id;
    private  String libelle;
    private String description;
    private  String status;
    private Date datedebut;
    private  Date datefin;
    private String imageUrl;
    private  List<User> users;
    private  List<NormeProjet>normes;
}
