package co.edu.unicauca.companyServices.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {



    private static final String EMPRESAS_COLA_QUEUE = "empresas_queue";
    private static final String PROYECTOS_QUEUE = "proyectos.queue";



    @Bean
    public Queue proyectosQueue() {
        return new Queue(PROYECTOS_QUEUE, true); // Cola durable
    }

    @Bean
    public Queue empresasQueue() {
        return new Queue(EMPRESAS_COLA_QUEUE, false);
    }


    // Configuración común
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());

        // Configuración adicional para confirmaciones
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                System.err.println("Message failed to reach exchange: " + cause);
            }
        });

        rabbitTemplate.setReturnsCallback(returned -> {
            System.err.println("Message returned: " + returned.toString());
        });

        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        factory.setPrefetchCount(10);
        factory.setDefaultRequeueRejected(false); // No reintentar mensajes fallidos
        return factory;
    }
}