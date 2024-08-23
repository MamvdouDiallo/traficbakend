package com.itma.gestionProjet.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ArticleEntente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String titre;
    private String description;

    @ManyToOne
    @JoinColumn(name = "ententeCompensation_id")
    private EntenteCompensation ententeCompensation;

}
