package com.gestion_academica.gestion_academica.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class GeneralController {
    
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/ayuda")
    public String ayuda() {
        return "ayuda";
    }

    @GetMapping("/acerca")
    public String acerca() {
        return "acerca";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

}
