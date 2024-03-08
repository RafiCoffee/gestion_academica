package com.gestion_academica.gestion_academica.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class GeneralController {
    @GetMapping("/")
    public String showIndex() {
        return "index";
    }

    @GetMapping("/ayuda")
    public String showAyuda() {
        return "ayuda";
    }

    @GetMapping("/acerca")
    public String showAcerca() {
        return "acerca";
    }
    
    @GetMapping("/error")
    public String showError() {
        return "error";
    }

}
