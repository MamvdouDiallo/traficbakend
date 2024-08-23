package com.itma.gestionProjet.dtos;

import com.itma.gestionProjet.entities.Image;
import com.itma.gestionProjet.requests.RoleRequest;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    private String lastname;
    private String firstname;
    private String email;
    private String date_of_birth;
    private String place_of_birth;
    private  String contact;
    private  String locality;
    private Boolean enabled;
    private Image image;
    private List<RoleDTO> roles;
    private String sous_role;
    private  List<ProjectDTOUSER> projects;
  //  private Set<ProjectDTO> projects;
}
