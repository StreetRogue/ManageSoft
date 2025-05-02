package co.edu.unicauca.usermicroservices.messaging;

import co.edu.unicauca.usermicroservices.entities.Usuario;
import co.edu.unicauca.usermicroservices.entities.enumTipoUsuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class MessageSender {

    // Metodo para enviar cualquier tipo de Usuario a RabbitMQ
    public void sendMessage(Usuario usuario) throws Exception {
        // Crear una conexión a RabbitMQ
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); // Dirección de tu RabbitMQ

        // Usamos ObjectMapper para convertir el objeto Usuario (de cualquier tipo) a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String usuarioJson = objectMapper.writeValueAsString(usuario);  // Convertir Usuario a JSON

        // Determinamos el nombre de la cola basado en el tipo de usuario
        String queueName = getQueueNameForUser(usuario);

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // Declarar la cola (asegurarse de que exista)
            channel.queueDeclare(queueName, false, false, false, null);

            // Enviar el JSON del usuario a RabbitMQ
            channel.basicPublish("", queueName, null, usuarioJson.getBytes(StandardCharsets.UTF_8));
            System.out.println("Usuario enviado a la cola " + queueName + ": " + usuarioJson);
        }
    }

    // Metodo que determina el nombre de la cola según el tipo de usuario
    private String getQueueNameForUser(Usuario usuario) {
        enumTipoUsuario tipoUsuario = usuario.getTipoUsuario();  // Obtener el tipo del usuario (estudiante, empresa, coordinador)

        // Asignar una cola diferente según el tipo de usuario
        switch (tipoUsuario) {
            case ESTUDIANTE:
                return "estudiantes_queue";  // Cola para estudiantes
            case EMPRESA:
                return "empresas_queue";  // Cola para empresas
            case COORDINADOR:
                return "coordinadores_queue";  // Cola para coordinadores
            default:
                return "usuarios_genericos_queue";  // Cola por defecto en caso de que no sea un tipo conocido
        }
    }
}
