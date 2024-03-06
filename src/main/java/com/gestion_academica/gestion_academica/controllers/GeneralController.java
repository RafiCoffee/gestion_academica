package com.gestion_academica.gestion_academica.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class GeneralController {
    @GetMapping("/")
    public String hola() {
        return "index";
    }

    @GetMapping("/error")
    public String showError() {
        return "error";
    }
    
}
