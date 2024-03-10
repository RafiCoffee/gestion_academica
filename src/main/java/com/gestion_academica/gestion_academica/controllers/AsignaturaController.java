package com.gestion_academica.gestion_academica.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gestion_academica.gestion_academica.models.Alumno;
import com.gestion_academica.gestion_academica.models.Asignatura;
import com.gestion_academica.gestion_academica.models.Profesor;
import com.gestion_academica.gestion_academica.repos.RepoAlumno;
import com.gestion_academica.gestion_academica.repos.RepoAsignatura;
import com.gestion_academica.gestion_academica.repos.RepoProfesor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;






@Controller
@RequestMapping("/asignaturas")
public class AsignaturaController {
    
    @Autowired
    private RepoAsignatura asignaturaRepositorio;

    @Autowired
    private RepoAlumno alumnoRepositorio;

    @Autowired
    private RepoProfesor profesorRepositorio;

    //Lista Asignaturas

    @GetMapping
    public String listarAsignaturas(Model modelo) {
        modelo.addAttribute("asignaturas", asignaturaRepositorio.findAll());
        return "asignaturas/asignaturas";
    }

    //Añadir Asignatura

    @GetMapping("/add")
    public String addAsignaturaGet(Model modelo) {
        modelo.addAttribute("asignatura", new Asignatura());
        return "asignaturas/add";
    }

    @PostMapping("/add")
    public String addAsignatura(@ModelAttribute("asignatura") @NonNull Asignatura asignatura) {
        asignaturaRepositorio.save(asignatura);
        return "redirect:/asignaturas";
    }

    //Editar Asignaturas

    @GetMapping("/edit/{id}")
    public String editAsignaturaForm(Model modelo, @PathVariable("id") @NonNull Long id) {
        Optional <Asignatura> objAsignatura = asignaturaRepositorio.findById(id);

        if(objAsignatura.isPresent()){
            modelo.addAttribute("asignatura", objAsignatura.get());
            return "asignaturas/edit";
        }else{
            modelo.addAttribute("mensaje", "La asignatura consultada no existe");
            return "error";
        }
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String editAsignatura(@PathVariable("id") @NonNull Long id, @ModelAttribute("asignatura") Asignatura asignaturaModificada) {
        Optional <Asignatura> objAsignatura = asignaturaRepositorio.findById(id);

        if(objAsignatura.isPresent()){
            Asignatura asignaturaExistente = objAsignatura.get();

            asignaturaExistente.setNombre(asignaturaModificada.getNombre());
            asignaturaExistente.setCurso(asignaturaModificada.getCurso());
            asignaturaExistente.setCiclo(asignaturaModificada.getCiclo());

            asignaturaRepositorio.save(asignaturaExistente);

            return "redirect:/asignaturas";
        }else{
            return "error";
        }
    }

    //Eliminar Asignatura

    @GetMapping("/delete/{id}")
    public String deleteAsignaturaForm(Model modelo, @PathVariable("id") @NonNull Long id) {
        Optional <Asignatura> objAsignatura = asignaturaRepositorio.findById(id);

        if(objAsignatura.isPresent()){
            modelo.addAttribute("asignatura", objAsignatura.get());
            return "asignatura/delete";
        }else{
            modelo.addAttribute("mensaje", "La asignatura no ha sido encontrada");
            return "error";
        }
    }

    @Transactional
    @PostMapping("/delete/{id}")
    public String deleteAsignatura(@PathVariable("id") @NonNull Long id) {
        Optional <Asignatura> objAsignatura = asignaturaRepositorio.findById(id);

        if(objAsignatura.isPresent()){
            Asignatura asignaturaEliminar = objAsignatura.get();

            for(Alumno alumno : asignaturaEliminar.getAlumnos()){
                alumno.getAsignaturas().remove(asignaturaEliminar);
                alumnoRepositorio.save(alumno);
            }

            Profesor profesorImparte = asignaturaEliminar.getProfesor();
            profesorImparte.getAsignaturas().remove(asignaturaEliminar);
            profesorRepositorio.save(profesorImparte);

            asignaturaRepositorio.deleteById(id);
            return "redirect:/asignaturas";
        }else{
            return "error";
        }
    }
    
    
    
    
    
    
    
}
