package com.itma.gestionProjet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.itma.gestionProjet.entities.PersonneAffecte;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "employe_pap")  // Spécifiez le nom de la table si nécessaire
public class EmployePap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codePap;
    private String codeEmploye;
    private String numeroIdentifiant;
    private String prenomNom;
    private String contactTelephonique;
    private String categorieActivite;
    private String sexe;
    private int age;
    private String nationalite;
    private String qualiteEmploye;
    private double remuneration;
    private double prime;
    private String handicap;  // Utilisez Boolean pour les valeurs booléennes
    private String parentcle;
    private String cle;
    private String childcle;
    private String infoComplementaire;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personne_affecte_id")
    private PersonneAffecte personneAffecte;
}
