package co.edu.unicauca.usermicroservices.messaging;

import co.edu.unicauca.usermicroservices.entities.Usuario;
import co.edu.unicauca.usermicroservices.services.UsuarioService;
import co.edu.unicauca.usermicroservices.messaging.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UsuarioDataSender implements CommandLineRunner {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MessageSender messageSender;

    @Override
    public void run(String... args) throws Exception {
        // Obtener todos los usuarios registrados
        Iterable<Usuario> usuarios = usuarioService.findAllUsers(); // Asumimos que tienes un metodo en tu servicio para obtener todos los usuarios.

        // Enviar a la cola
        for (Usuario usuario : usuarios) {
            try {
                messageSender.sendMessage(usuario);
                System.out.println("Usuario enviado a la cola: " + usuario);
            } catch (Exception e) {
                System.out.println("Error al enviar el usuario a RabbitMQ: " + e.getMessage());
            }
        }
    }
}