package com.gestion_academica.gestion_academica.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion_academica.gestion_academica.models.Profesor;

public interface RepoProfesor extends JpaRepository<Profesor, Long> {
    
}
