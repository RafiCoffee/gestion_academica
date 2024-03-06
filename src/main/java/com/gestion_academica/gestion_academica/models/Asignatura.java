package com.gestion_academica.gestion_academica.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Asignatura {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(length = 30)
    private String nombre;

    @Column(length = 10)
    private String curso;

    @Column(length = 30)
    private String ciclo;

    @ManyToMany
    @JoinTable(name = "asignatura_alumno")
    private List<Alumno> alumnos;

    @ManyToOne
    @JoinColumn(name = "profesor_id")
    private Profesor profesor;
}
