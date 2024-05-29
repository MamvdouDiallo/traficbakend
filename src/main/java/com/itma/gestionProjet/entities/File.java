package com.itma.gestionProjet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name ;
    private String type ;
    @Column( name = "FILE" , length = 4048576 )
    @Lob
    private byte[] file;
    @ManyToOne
    @JoinColumn (name="PROJECT_ID")
    @JsonIgnore
    private Project project;


}
