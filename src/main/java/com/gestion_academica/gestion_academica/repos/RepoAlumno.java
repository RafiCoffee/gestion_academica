package com.gestion_academica.gestion_academica.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gestion_academica.gestion_academica.models.Alumno;

public interface RepoAlumno extends JpaRepository<Alumno, Long>{
    
    @Query(value = "SELECT * FROM alumno WHERE username = :username", nativeQuery = true)
    Alumno findByUsername(@Param("username") String username);

}
