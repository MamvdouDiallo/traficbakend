package com.itma.gestionProjet.entities;



import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Plainte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroDossier;
    private String lieuEnregistrement;
    private Date dateEnregistrement;
    private Boolean isRecensed;
    private String lieuNaissance;
    private String nom;
    private String prenom;
    private String numeroIdentification;
    private String placeOfBirth;
    private String recommandation;
    private String situationMatrimoniale;
    private String typeIdentification;
    private String vulnerabilite;
    private String codePap;
    private  String etat;
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project projet;

    @ElementCollection
    @Column(name = "document_url")
    private List<String> documentUrls;

    // Getters and Setters
}