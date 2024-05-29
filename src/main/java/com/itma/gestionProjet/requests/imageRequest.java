package com.itma.gestionProjet.requests;

import com.itma.gestionProjet.entities.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class imageRequest {
    //   private Integer idImage ;
      private    String name;
      private  String  type  ;
      private Integer[]  image ;
      private Project project;
    }

