package com.itma.gestionProjet.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class Betails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codePap;
    private String typeBetails;
    private String description;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "personneAffecte_id")
    private PersonneAffecte personneAffecte;
}
