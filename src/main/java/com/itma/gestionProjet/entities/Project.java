package com.itma.gestionProjet.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String libelle;
    private String description;
    private String status;
    private String categorie;
    private Date datedebut;
    private Date datefin;
    @OneToOne
    private  Image image;
    @OneToMany(mappedBy = "project")
    private List<File> files;
}
