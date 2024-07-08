package com.itma.gestionProjet.requests;

import com.itma.gestionProjet.entities.File;
import com.itma.gestionProjet.entities.Image;
import com.itma.gestionProjet.entities.Project;
import com.itma.gestionProjet.entities.User;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;



@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequest extends Project {
    @NotBlank(message = "Name cannot be blank")
    @NotNull(message = "name cannot be null")
    private  String libelle;
    @NotBlank(message = "Description cannot be blank")
    @NotNull(message = "description cannot be null")
    private String description;
    @NotBlank(message = "Status cannot be blank")
    @NotNull(message = "Status cannot be null")
    private  String status;
    @NotBlank(message = "Categorie cannot be blank")
    @NotNull(message = "Categorie cannot be null")
    private String categorie;
    @NotNull(message = "Start date cannot be null")
    private Date datedebut;
    @NotNull(message = "End date cannot be null")
    private  Date datefin;
    private Image image;
    private List<File> files;
    private  List<User> users;
}
