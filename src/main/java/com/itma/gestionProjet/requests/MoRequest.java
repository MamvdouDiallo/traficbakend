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
    private String date_of_birth;
    private String place_of_birth;
    private Boolean enabled;
    private String password;
    private  String locality;
    private  String contact;
    private Image image;
    private  String imageUrl;
    private List<RoleRequest> roles;
    private  List<Long> project_ids;
}
