package co.edu.unicauca.usermicroservices.controllers;


import co.edu.unicauca.usermicroservices.entities.Estudiante;
import co.edu.unicauca.usermicroservices.entities.Usuario;
import co.edu.unicauca.usermicroservices.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")

public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    // Endpoint para registrar un usuario
    @PostMapping("/register")
    public Usuario register(@RequestBody Usuario usuario) {
        return usuarioService.register(usuario);
    }

    // Endpoint para login de usuario
    @PostMapping("/login")
    public Usuario login(@RequestParam String nombreUsuario, @RequestParam String contrasena) {
        return usuarioService.login(nombreUsuario, contrasena);
    }




}
