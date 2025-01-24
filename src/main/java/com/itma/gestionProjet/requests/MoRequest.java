package com.itma.gestionProjet.requests;

import com.itma.gestionProjet.entities.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoRequest {
  private Long id;
    private String lastname;
    private String firstname;
    private String email;
    private  String locality;
    private  String contact;
    private  String imageUrl;
   // private  List<Long> project_ids;
}
