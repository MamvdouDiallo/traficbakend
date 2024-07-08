package com.itma.gestionProjet.entities;

import jakarta.persistence.*;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pays")
public class Pays {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @Column(name = "code_alpha2", length = 2)
    private String codeAlpha2;

    @Column(name = "code_alpha3", length = 3)
    private String codeAlpha3;

    @Column(name = "code_alpha_devise")
    private String codeAlphaDevise;

    @Column(name = "code_num")
    private String codeNum;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Column(name = "libelle_nationalite")
    private String libelleNationalite;

    @Column(name = "libelle_pays")
    private String libellePays;

    @Column(name = "prefixe_telephonique")
    private String prefixeTelephonique;

    @Column(name = "user_create")
    private String userCreate;

    @Column(name = "user_update")
    private String userUpdate;
}
