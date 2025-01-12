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

    private String libelleProjet;
    private String numeroDossier;
    private String lieuEnregistrement;
    private Date dateEnregistrement;
    private Boolean isRecensed;
    private Boolean isSignedFileRecensement;
    private Date dateRecensement;
    private String natureBienAffecte;
    private String emplacementBienAffecte;
    private String typeIdentification;
    private String numeroIdentification;
    private String contact;
    private String prenom;
    private String nom;
    private String codePap;
    private String vulnerabilite;
    private String email;
    private String situationMatrimoniale;
    private String descriptionObjet;
    private Boolean hasDocument;
    private String recommandation;
    private String etat;

    // Liste des URLs des documents
    @ElementCollection
    @Column(name = "document_url")
    private List<String> documentUrls;

    // Signature des papiers
    private String urlSignaturePap;
    private String urlSignatureResponsable;

    // Relation avec l'entité Project
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project projet;
}