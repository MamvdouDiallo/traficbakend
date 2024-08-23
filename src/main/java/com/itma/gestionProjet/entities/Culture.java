package com.itma.gestionProjet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Culture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codePap;
    private String typeCulture;
    private String description;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "personneAffecte_id")
    private PersonneAffecte personneAffecte;
}
