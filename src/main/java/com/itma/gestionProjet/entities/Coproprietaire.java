package com.itma.gestionProjet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Coproprietaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String prenomNom;
    private String contactTelephonique;
    private String codePap;
    private String codeCoProprietaire;
    private String sexe;
    private int age;
    private String situationMatrimoniale;
    private String photo;
    private String infoComplementaire;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personne_affecte_id")
    private PersonneAffecte personneAffecte;
}
