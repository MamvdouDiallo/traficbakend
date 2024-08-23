package com.itma.gestionProjet.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EntenteCompensation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelleProjet;
    private String codePap;
    private String prenom;
    private String nom;
    private Date dateNaissance;
    private String lieuNaissance;
    private String localiteEnregistrement;
    private  String dateEnregistrement;
    private String naturePiece;
    private String numeroPiece;

    @OneToMany(mappedBy = "ententeCompensation", cascade = CascadeType.ALL)
    private List<ArticleEntente> articles;



}