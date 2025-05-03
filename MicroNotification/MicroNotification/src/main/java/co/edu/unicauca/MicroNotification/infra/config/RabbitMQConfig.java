package co.edu.unicauca.MicroNotification.infra.config;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String COLA_NOTIFICATION = "cola.notificacion";
    public static final String COLA_COMENTARIO = "cola.comentario";
    public static final String COLA_COMENTARIO_EMPRESA = "cola.comentarioEmpresa";

    @Bean
    public Queue notificacionQueue() {
        return new Queue(COLA_NOTIFICATION, true);
    }

    @Bean
    public Queue comentarioQueue() {
        return new Queue(COLA_COMENTARIO, true);
    }

    @Bean
    public Queue comentarioEmpresaQueue() {
        return new Queue(COLA_COMENTARIO_EMPRESA, true);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}

