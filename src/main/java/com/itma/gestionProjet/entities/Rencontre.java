package com.itma.gestionProjet.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Rencontre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;
    private String libelle;

    @Column(name = "url_pv_rencontre")
    private String urlPvRencontre;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
}
