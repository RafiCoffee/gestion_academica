package com.gestion_academica.gestion_academica.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GestorController {
    
    @GetMapping("/login")
    public String mostrarFormularioLogin(){
        return "login";
    }
}
