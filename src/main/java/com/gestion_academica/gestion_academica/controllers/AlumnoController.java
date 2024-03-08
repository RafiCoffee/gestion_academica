package com.gestion_academica.gestion_academica.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gestion_academica.gestion_academica.models.Alumno;
import com.gestion_academica.gestion_academica.models.Asignatura;
import com.gestion_academica.gestion_academica.models.Matricula;
import com.gestion_academica.gestion_academica.models.Usuario;
import com.gestion_academica.gestion_academica.repos.RepoAlumno;
import com.gestion_academica.gestion_academica.repos.RepoAsignatura;
import com.gestion_academica.gestion_academica.repos.RepoUsuario;

import jakarta.transaction.Transactional;


@Controller
@RequestMapping("/alumnos")
public class AlumnoController {
    
    @Autowired
    private RepoAlumno alumnoRepositorio;
    
    @Autowired
    private RepoAsignatura asignaturaRepositorio;

    @Autowired
    private RepoUsuario usuarioRepositorio;

    private final PasswordEncoder encoder;

    public AlumnoController(PasswordEncoder encoder){
        this.encoder = encoder;
    }

    //Lista Alumnos

    @GetMapping
    public String listarAlumnos(Model modelo){
        modelo.addAttribute("alumnos", alumnoRepositorio.findAll());
        return "alumnos/alumnos";
    }

    //Asignaturas Matriculadas Alumno

    @GetMapping("/matriculas/{id}")
    public String asignaturasMatriculadas(Model modelo, @PathVariable("id") @NonNull Long id){
        Alumno alumnoMatriculado;
        if(id != 0){
            Optional <Alumno> objAlumno = alumnoRepositorio.findById(id);

            if(objAlumno.isPresent()){ alumnoMatriculado = objAlumno.get(); } else {
                modelo.addAttribute("mensaje", "El alumno consultado no existe.");
                return "error";
            }
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String nombreAlumnoLogueado = authentication.getName();

            alumnoMatriculado = alumnoRepositorio.findByUsername(nombreAlumnoLogueado);

            if(alumnoMatriculado == null){
                modelo.addAttribute("mensaje", "El profesor consultado no existe.");
                return "error";
            }
        }

        List<Asignatura> asignaturasMatriculadas = alumnoMatriculado.getAsignaturas();

            modelo.addAttribute("alumno", alumnoMatriculado);
            modelo.addAttribute("asignaturas", asignaturasMatriculadas);
            return "alumnos/matriculas";
    }

    //Añadir Alumno

    @GetMapping("/add")
    public String addAlumnoGet(Model modelo) {
        modelo.addAttribute("alumno", new Alumno());
        return "alumnos/add";
    }

    @PostMapping("/add")
    public String addAlumno(@ModelAttribute("alumno") @NonNull Alumno alumno) {
        alumno.setPassword(encoder.encode(alumno.getPassword()));
        alumno.setAuthority("ALUMNO");
        alumnoRepositorio.save(alumno);


        Usuario usuario = new Usuario();
        usuario.setUsername(alumno.getUsername());
        usuario.setPassword(alumno.getPassword());
        usuario.setAuthority(alumno.getAuthority());
        usuario.setAlumno(alumno);
        usuarioRepositorio.save(usuario);
        return "redirect:/alumnos";
    }

    //Editar Alumno

    @GetMapping("/edit/{id}")
    public String editAlumnoForm(Model modelo, @PathVariable("id") @NonNull Long id) {
        Optional <Alumno> objAlumno = alumnoRepositorio.findById(id);
        if(objAlumno.isPresent()) {
            modelo.addAttribute(
            "alumno", objAlumno.get());
            return "alumnos/edit";
        } else {
            modelo.addAttribute("mensaje", "El alumno consultado no existe.");
            return "error";
        }
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String editAlumno(@PathVariable("id") @NonNull Long id, @ModelAttribute("alumnoModificado") Alumno alumnoModificado) {
        Optional <Alumno> objAlumno = alumnoRepositorio.findById(id);

        if(objAlumno.isPresent()){
            Alumno alumnoExistente = objAlumno.get();
            Usuario usuarioExistente = usuarioRepositorio.findByAlumnoId(id);

            alumnoExistente.setNombre(alumnoModificado.getNombre());
            alumnoExistente.setApellidos(alumnoModificado.getApellidos());
            alumnoExistente.setTelefono(alumnoModificado.getTelefono());
            alumnoExistente.setEmail(alumnoModificado.getEmail());
            alumnoExistente.setUsername(alumnoModificado.getUsername());

            usuarioExistente.setUsername(alumnoModificado.getUsername());

            alumnoRepositorio.save(alumnoExistente);
            usuarioRepositorio.save(usuarioExistente);

            return "redirect:/alumnos";
        }else{
            return "error";
        }
    }

    //Eliminar Alumno

    @GetMapping("/delete/{id}")
    public String deleteAlumnoForm(Model modelo, @PathVariable("id") @NonNull Long id) {
        Optional <Alumno> objAlumno = alumnoRepositorio.findById(id);
        if (objAlumno.isPresent()){
            modelo.addAttribute(
            "alumno", objAlumno.get());
            return "alumnos/delete";
        } else {
            modelo.addAttribute(
                "mensaje", "El alumno consultado no existe.");
            return "error";
        }
    }

    @Transactional
    @PostMapping("/delete/{id}")
    public String deleteAlumno(@PathVariable("id") @NonNull Long id) {
        usuarioRepositorio.deleteByAlumnoId(id);
        alumnoRepositorio.deleteById(id);
        return "redirect:/alumnos";
    }

    //Matricular Alumno

    @GetMapping("/matricular/{id}")
    public String matricularAlumnoForm(Model modelo, @PathVariable("id") @NonNull Long id) {
        Optional <Alumno> objAlumno = alumnoRepositorio.findById(id);
        if(objAlumno.isPresent()) {
            modelo.addAttribute("alumno", objAlumno.get());
            modelo.addAttribute("asignaturas", asignaturaRepositorio.findAll());
            modelo.addAttribute("matricula", new Matricula());
            return "alumnos/matricular";
        } else {
            modelo.addAttribute("mensaje", "El alumno consultado no existe.");
            return "error";
        }
    }

    @RequestMapping(value = "/matricular/{id}", method = RequestMethod.POST)
    public String matricularAlumno(@PathVariable("id") @NonNull Long idAlumno, @ModelAttribute("matricula") @NonNull Matricula matricula) {
        Optional <Alumno> objAlumno = alumnoRepositorio.findById(idAlumno);
        Long idAsignatura = matricula.getAsignaturaId();

        if(objAlumno.isPresent() && idAsignatura != null){
            Alumno alumnoMatriculado = objAlumno.get();
            Asignatura asignaturaMatriculada = asignaturaRepositorio.findById(idAsignatura).get();

            alumnoMatriculado.getAsignaturas().add(asignaturaMatriculada);
            asignaturaMatriculada.getAlumnos().add(alumnoMatriculado);

            alumnoRepositorio.save(alumnoMatriculado);
            asignaturaRepositorio.save(asignaturaMatriculada);

            return "redirect:/alumnos";
        }else{
            return "error";
        }
    }
}
