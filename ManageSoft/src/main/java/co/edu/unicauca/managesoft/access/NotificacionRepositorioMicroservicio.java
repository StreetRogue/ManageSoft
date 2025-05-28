/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.access;

/**
 *
 * @author camac
 */
import co.edu.unicauca.managesoft.entities.Coordinador;
import co.edu.unicauca.managesoft.entities.Correo;
import co.edu.unicauca.managesoft.entities.Estudiante;
import co.edu.unicauca.managesoft.entities.Proyecto;
import co.edu.unicauca.managesoft.infra.ProyectTable;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
public class NotificacionRepositorioMicroservicio implements INotificacionRepositorio {

    private static final String RABBITMQ_HOST = "localhost"; // Dirección del servidor RabbitMQ
    private static final String QUEUE_NAME = "cola.notificacion"; // Nombre de la cola
    private static final String QUEUE_NAMEC = "cola.comentario";
    private static final String BASE_URL = "http://localhost:8082/api";

    @Override
    public boolean enviarCorreo(Correo correo, Estudiante estudiante, Proyecto proyecto) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RABBITMQ_HOST);
        factory.setUsername("ManageSoft");
        factory.setPassword("oracle");

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {

            // Declarar la cola
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            // Crear el mensaje en formato JSON
            String mensaje = "{\"emailEstudiante\": \"" + estudiante.getEmailEstudiante() + "\","
                    + "\"emailCoordinador\": \"" + correo.getDestinatario() + "\","
                    + "\"idProyecto\": \"" + proyecto.getIdProyecto() + "\","
                    + "\"asunto\": \"" + correo.getAsunto() + "\","
                    + "\"motivo\": \"" + correo.getMensaje() + "\","
                    + "\"estado\": \"ENVIADO\"}";

            // Publicar el mensaje en la cola de RabbitMQ
            channel.basicPublish("", QUEUE_NAME, null, mensaje.getBytes());
            System.out.println(" [x] Correo enviado a RabbitMQ: " + mensaje);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean enviarComentario(String comentario, Coordinador coordinador, Proyecto proyecto) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RABBITMQ_HOST);
        factory.setUsername("ManageSoft");
        factory.setPassword("oracle");

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {

            // Declarar la cola
            channel.queueDeclare(QUEUE_NAMEC, true, false, false, null);

            // Crear el mensaje en formato JSON
            String mensaje = "{\"comentario\": \"" + comentario + "\","
                    + "\"emailCoordinador\": \"" + coordinador.getEmail() + "\","
                    + "\"emailEmpresa\": \"" + proyecto.getEmpresa().getEmailEmpresa() + "\","
                    + "\"idProyecto\": \"" + proyecto.getIdProyecto() + "\"}";

            // Publicar el mensaje en la cola de RabbitMQ
            channel.basicPublish("", QUEUE_NAMEC, null, mensaje.getBytes());
            System.out.println(" [x] Comentario enviado a RabbitMQ: " + mensaje);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

@Override
public int cantidadComentarios(Coordinador coordinador) {
    try {
        String email = URLEncoder.encode(coordinador.getEmail(), "UTF-8");
        String urlStr = BASE_URL + "/comentarios/contador?email=" + email;

        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        if (conn.getResponseCode() == 200) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                String line = reader.readLine();
                if (line != null && !line.isEmpty()) {
                    return Integer.parseInt(line.trim());
                }
            }
        } else {
            System.err.println("Respuesta HTTP inesperada: " + conn.getResponseCode());
        }

    } catch (Exception e) {
        System.err.println("Error al obtener cantidad de comentarios: " + e.getMessage());
        e.printStackTrace();
    }

    return 0;
}

}
