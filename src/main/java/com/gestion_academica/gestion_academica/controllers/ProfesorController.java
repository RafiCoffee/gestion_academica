package com.gestion_academica.gestion_academica.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gestion_academica.gestion_academica.models.Asignatura;
import com.gestion_academica.gestion_academica.models.Imparte;
import com.gestion_academica.gestion_academica.models.Profesor;
import com.gestion_academica.gestion_academica.models.Usuario;
import com.gestion_academica.gestion_academica.repos.RepoAsignatura;
import com.gestion_academica.gestion_academica.repos.RepoProfesor;
import com.gestion_academica.gestion_academica.repos.RepoUsuario;

import jakarta.transaction.Transactional;

@Controller
@RequestMapping("/profesores")
public class ProfesorController {
    
    @Autowired
    private RepoProfesor profesorRepositorio;

    @Autowired
    private RepoAsignatura asignaturaRepositorio;

    @Autowired
    private RepoUsuario usuarioRepositorio;

    private final PasswordEncoder encoder;

    public ProfesorController(PasswordEncoder encoder){
        this.encoder = encoder;
    }

    //Lista Profesor

    @GetMapping
    public String listarProfesores(Model modelo){
        modelo.addAttribute("profesores", profesorRepositorio.findAll());
        return "profesores/profesores";
    }

    //Asignaturas Impartidas Profesor

    @GetMapping("/imparte/{id}")
    public String asignaturasImpartidas(Model modelo, @PathVariable("id") @NonNull Long id){
        Profesor profesorAsignado;
        if(id != 0){
            Optional <Profesor> objProfesor = profesorRepositorio.findById(id);

            if(objProfesor.isPresent()){ profesorAsignado = objProfesor.get(); } else {
                modelo.addAttribute("mensaje", "El profesor consultado no existe.");
                return "error";
            }
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String nombreProfesorLogueado = authentication.getName();

            profesorAsignado = profesorRepositorio.findByUsername(nombreProfesorLogueado);

            if(profesorAsignado == null){
                modelo.addAttribute("mensaje", "El profesor consultado no existe.");
                return "error";
            }
        }

        List<Asignatura> asignaturasImpartidas = profesorAsignado.getAsignaturas();

        modelo.addAttribute("profesor", profesorAsignado);
        modelo.addAttribute("asignaturas", asignaturasImpartidas);
        return "profesores/imparte";
    }

    //Añadir Profesor

    @GetMapping("/add")
    public String addProfesorGet(Model modelo) {
        modelo.addAttribute("profesor", new Profesor());
        return "profesores/add";
    }

    @PostMapping("/add")
    public String addProfesor(@ModelAttribute("profesor") @NonNull Profesor profesor) {
        profesor.setPassword(encoder.encode(profesor.getPassword()));
        profesor.setAuthority("PROFESOR");
        profesorRepositorio.save(profesor);


        Usuario usuario = new Usuario();
        usuario.setUsername(profesor.getUsername());
        usuario.setPassword(profesor.getPassword());
        usuario.setAuthority(profesor.getAuthority());
        usuario.setProfesor(profesor);
        usuarioRepositorio.save(usuario);
        return "redirect:/profesores";
    }

    //Editar Profesor

    @GetMapping("/edit/{id}")
    public String editProfesorForm(Model modelo, @PathVariable("id") @NonNull Long id) {
        Optional <Profesor> objProfesor = profesorRepositorio.findById(id);
        if(objProfesor.isPresent()) {
            modelo.addAttribute(
            "profesor", objProfesor.get());
            return "profesores/edit";
        } else {
            modelo.addAttribute("mensaje", "El profesor consultado no existe.");
            return "error";
        }
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String editProfesor(@PathVariable("id") @NonNull Long id, @ModelAttribute("profesorModificado") Profesor profesorModificado) {
        Optional <Profesor> objProfesor = profesorRepositorio.findById(id);

        if(objProfesor.isPresent()){
            Profesor profesorExistente = objProfesor.get();
            Usuario usuarioExistente = usuarioRepositorio.findByProfesorId(id);

            profesorExistente.setNombre(profesorModificado.getNombre());
            profesorExistente.setApellidos(profesorModificado.getApellidos());
            profesorExistente.setEmail(profesorModificado.getEmail());
            profesorExistente.setUsername(profesorModificado.getUsername());

            usuarioExistente.setUsername(profesorModificado.getUsername());

            profesorRepositorio.save(profesorExistente);
            usuarioRepositorio.save(usuarioExistente);

            return "redirect:/profesores";
        }else{
            return "error";
        }
    }

    //Eliminar Profesor

    @GetMapping("/delete/{id}")
    public String deleteProfesorForm(Model modelo, @PathVariable("id") @NonNull Long id) {
        Optional <Profesor> objProfesor = profesorRepositorio.findById(id);
        if (objProfesor.isPresent()){
            modelo.addAttribute(
            "profesor", objProfesor.get());
            return "profesores/delete";
        } else {
            modelo.addAttribute(
                "mensaje", "El profesor consultado no existe.");
            return "error";
        }
    }

    @Transactional
    @PostMapping("/delete/{id}")
    public String deleteProfesor(@PathVariable("id") @NonNull Long id) {
        usuarioRepositorio.deleteByProfesorId(id);
        profesorRepositorio.deleteById(id);
        return "redirect:/profesores";
    }

    //Impartir Asignatura

    @GetMapping("/impartir/{id}")
    public String asignarProfesorForm(Model modelo, @PathVariable("id") @NonNull Long id) {
        Optional <Profesor> objProfesor = profesorRepositorio.findById(id);
        if(objProfesor.isPresent()) {
            modelo.addAttribute("profesor", objProfesor.get());
            modelo.addAttribute("asignaturas", asignaturaRepositorio.findAll());
            modelo.addAttribute("imparte", new Imparte());
            return "profesores/impartir";
        } else {
            modelo.addAttribute("mensaje", "El profesor consultado no existe.");
            return "error";
        }
    }

    @RequestMapping(value = "/impartir/{id}", method = RequestMethod.POST)
    public String asignarProfesor(@PathVariable("id") @NonNull Long idProfesor, @ModelAttribute("imparte") @NonNull Imparte imparte) {
        Optional <Profesor> objProfesor = profesorRepositorio.findById(idProfesor);
        Long idAsignatura = imparte.getAsignaturaId();

        if(objProfesor.isPresent() && idAsignatura != null){
            Profesor profesorAsignado = objProfesor.get();
            Asignatura asignaturaImpartida = asignaturaRepositorio.findById(idAsignatura).get();

            profesorAsignado.getAsignaturas().add(asignaturaImpartida);
            asignaturaImpartida.setProfesor(profesorAsignado);;

            profesorRepositorio.save(profesorAsignado);
            asignaturaRepositorio.save(asignaturaImpartida);

            return "redirect:/profesores";
        }else{
            return "error";
        }
    }
}
