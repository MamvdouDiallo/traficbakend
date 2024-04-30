package com.itma.gestionProjet.requests;

import lombok.*;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private Long id;
    private String lastname;
    private String firstname;
    private String email;
    private String date_of_birth;
    private String place_of_birth;
    private Boolean enabled;
    private List<RoleRequest> roles;
    public String getEmail() {
        return this.email;
    }
}
