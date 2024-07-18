package com.itma.gestionProjet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String date_of_birth;
    private String place_of_birth;
    private Boolean enabled;
    private String password;
    private  String locality;
    private String contact;
    private  String sexe;
@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles ;
    @OneToOne
    private  Image image;

    @ManyToMany(mappedBy = "users", cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    private List<Project> projects;


    public void addProject(Project project) {
        this.projects.add(project);
        project.getUsers().add(this);
    }

    public void removeProject(Project project) {
        this.projects.remove(project);
        project.getUsers().remove(this);
    }

}
