package co.edu.unicauca.companyServices.services;

import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducerService {


    /*
    @EventListener(ApplicationReadyEvent.class)
    public void enviarProyectos(Proyecto proyecto1) {
    try {
        List<Proyecto> proyectos;
        proyectos =proyectoService.listarProyectos();

        for(Proyecto proyecto:proyectos){
            ProyectoEstudianteDTO dto = proyectoMapper.toProyectoEstudianteDTO(proyecto);
            rabbitTemplate.convertAndSend("proyectos_queue", dto);
        }

    } catch (Exception e) {
        System.err.println("Error al procesar los proyectos " + e.getMessage());
        throw new AmqpRejectAndDontRequeueException(e);
    }

}
*/
/*
    public void enviarProyecto(Proyecto proyecto1) {
        try {
            ProyectoEstudianteDTO dto = proyectoMapper.toProyectoEstudianteDTO(proyecto1);
            rabbitTemplate.convertAndSend("proyectos_queue", dto);

        } catch (Exception e) {
            System.err.println("Error al procesar el proyecto" + e.getMessage());
            throw new AmqpRejectAndDontRequeueException(e);
        }

    }
    */

}