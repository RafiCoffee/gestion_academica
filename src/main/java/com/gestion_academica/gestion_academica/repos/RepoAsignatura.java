package com.gestion_academica.gestion_academica.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion_academica.gestion_academica.models.Asignatura;

public interface RepoAsignatura extends JpaRepository<Asignatura, Long>{
    
}
