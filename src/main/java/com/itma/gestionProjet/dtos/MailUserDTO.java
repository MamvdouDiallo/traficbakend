package com.itma.gestionProjet.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MailUserDTO {
    private Long id;
    private String nomComplet;
    private String email;
  //  private String numeroTelephone;
   // private String object;
    //private String motif;
    private String contenu;
    private LocalDateTime dateCreation;
}
