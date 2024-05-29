package com.itma.gestionProjet.dtos;

import com.itma.gestionProjet.entities.Image;
import com.itma.gestionProjet.requests.RoleRequest;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String lastname;
    private String firstname;
    private String email;
    private String date_of_birth;
    private String place_of_birth;
    private Boolean enabled;
    private Image image;
    private List<RoleDTO> roles;
}
