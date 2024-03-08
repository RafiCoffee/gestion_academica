package com.gestion_academica.gestion_academica.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gestion_academica.gestion_academica.models.Profesor;

public interface RepoProfesor extends JpaRepository<Profesor, Long> {

    @Query(value = "SELECT * FROM profesor WHERE username = :username", nativeQuery = true)
    Profesor findByUsername(@Param("username") String username);
    
}
