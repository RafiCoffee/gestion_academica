package com.gestion_academica.gestion_academica.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gestion_academica.gestion_academica.repos.RepoAsignatura;

@Controller
@RequestMapping("/asignaturas")
public class AsignaturaController {
    
    @Autowired
    private RepoAsignatura asignaturaRepositorio;

    @GetMapping
    public String listarAsignaturas(Model modelo){
        modelo.addAttribute("asignaturas", asignaturaRepositorio.findAll());
        return "asignaturas/lista";
    }
}
