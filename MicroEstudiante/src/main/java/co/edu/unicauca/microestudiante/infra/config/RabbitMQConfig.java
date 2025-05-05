package co.edu.unicauca.microestudiante.infra.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

@Configuration
public class RabbitMQConfig {
    public static final String COLA_POSTULACION = "ColaPostulacion";
    public static final String COLA_PROYECTOS = "proyectos_queue";
    public static final String COLA_NOTIFIACIONCOORDINADOR = "cola.notificacion";
    public static final String COLA_ESTUDIANTESREGISTRADOS = "estudiantes_queue";

    @Bean
    public Queue ColaPostulacion() {
        return new Queue(COLA_POSTULACION, true);
    }

    @Bean
    public Queue ColaProyectosAceptados() {
        return new Queue(COLA_PROYECTOS, false);
    }

    @Bean
    public Queue ColaNotificacionCoordinador() {
        return new Queue(COLA_NOTIFIACIONCOORDINADOR, true);
    }

    @Bean
    public Queue ColaEstudiantesRegistrados() {
        return new Queue(COLA_ESTUDIANTESREGISTRADOS, false);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}