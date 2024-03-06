package com.gestion_academica.gestion_academica.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gestion_academica.gestion_academica.repos.RepoProfesor;

@Controller
@RequestMapping("/profesores")
public class ProfesorController {
    
    @Autowired
    private RepoProfesor profesorRepositorio;

    @GetMapping
    public String listarProfesores(Model modelo){
        modelo.addAttribute("profesores", profesorRepositorio.findAll());
        return "profesores/lista";
    }
}
