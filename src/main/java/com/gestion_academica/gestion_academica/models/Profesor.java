package com.gestion_academica.gestion_academica.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Profesor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(length = 25)
    private String usuario;

    @Column(length = 100)
    private String password;

    @Column(length = 100)
    private String nombre;
    private String apellidos;

    @Column(length = 100)
    private String email;

    @OneToMany
    private List<Asignatura> asignaturas;
}
