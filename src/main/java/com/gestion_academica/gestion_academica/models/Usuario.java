package com.gestion_academica.gestion_academica.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 25, unique = true)
    private String username;

    @Column(length = 100)
    private String password;

    @Column(length = 15)
    private String authority;

    @OneToOne
    private Gestor gestor;

    @OneToOne
    private Alumno alumno;

    @OneToOne
    private Profesor profesor;
}
