package co.edu.unicauca.usermicroservices.infrastructure.input.rest;


import co.edu.unicauca.usermicroservices.DTO.LoginRequest;
import co.edu.unicauca.usermicroservices.domain.model.Usuario;
import co.edu.unicauca.usermicroservices.aplication.services.UsuarioService;
import co.edu.unicauca.usermicroservices.infrastructure.output.messaging.MessageSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")

public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MessageSender messageSender;

    // Endpoint para registrar un usuario
    @PostMapping("/register")
    public Usuario register(@RequestBody Usuario usuario) {
        System.out.println("Cuerpo del JSON recibido: " + usuario);
        System.out.println("Tipo de usuario recibido DESDE EL CONTROLADOR: " + usuario.getTipoUsuario());

        // Registra el usuario (esto incluiría la creación del usuario en la base de datos)
        Usuario registrado = usuarioService.register(usuario);

        try {
            messageSender.sendMessage(usuario);  // Llamamos al servicio de RabbitMQ para enviar el mensaje
            System.out.println("Usuario enviado a la cola: " + usuario);
        } catch (Exception e) {
            System.out.println("Error al enviar el usuario a RabbitMQ: " + e.getMessage());
        }

        return registrado;
    }

    // Endpoint para login de usuario
    @PostMapping("/login")
    public Usuario login(@RequestBody LoginRequest loginRequest) {
        return usuarioService.login(loginRequest.getNombreUsuario(), loginRequest.getContrasena());
    }
}
