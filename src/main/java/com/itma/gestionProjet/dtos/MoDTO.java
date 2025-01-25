package com.itma.gestionProjet.dtos;

import com.itma.gestionProjet.entities.Image;
import lombok.Data;

import java.util.List;


@Data
public class MoDTO {
    private Long id;
    private String lastname;
    private String firstname;
    private String email;
    private  String contact;
    private  String locality;
    private Boolean enabled;
    private List<RoleDTO> roles;
}
