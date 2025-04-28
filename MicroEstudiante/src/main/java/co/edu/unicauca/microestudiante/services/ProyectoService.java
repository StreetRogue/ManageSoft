package co.edu.unicauca.microestudiante.services;

import co.edu.unicauca.microestudiante.access.IProyectoRepositorio;
import co.edu.unicauca.microestudiante.entities.Proyecto;
import co.edu.unicauca.microestudiante.infra.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProyectoService {
    @Autowired
    private IProyectoRepositorio proyectoRepositorio;

    @RabbitListener(queues = RabbitMQConfig.COLA_PROYECTOSACEPTADOS)
    public void proyectosAceptados(Proyecto proyecto) {
        proyectoRepositorio.save(proyecto);
    }
}
