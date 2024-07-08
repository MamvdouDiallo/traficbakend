package com.itma.gestionProjet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String libelle;
    private String description;
    private String status;
    private String categorie;
    private Date datedebut;
    private Date datefin;
    @OneToOne
    private  Image image;
    @OneToMany(mappedBy = "project")
    private List<File> files;
    @OneToMany(mappedBy = "project")
    private List<NormeProjet> normeProjets;
   // @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ManyToMany(fetch = FetchType.LAZY,cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "user_project",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private List<User> users;

    public void addUser(User user) {
        this.users.add(user);
        user.getProjects().add(this);
    }

    public void removeUser(User user) {
        this.users.remove(user);
        user.getProjects().remove(this);
    }
}
