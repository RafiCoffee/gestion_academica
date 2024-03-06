package com.gestion_academica.gestion_academica.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion_academica.gestion_academica.models.Alumno;

public interface RepoAlumno extends JpaRepository<Alumno, Long>{
    
}
