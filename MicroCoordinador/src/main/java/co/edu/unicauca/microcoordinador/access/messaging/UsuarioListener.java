package co.edu.unicauca.microcoordinador.access.messaging;

import co.edu.unicauca.microcoordinador.entities.Coordinador;
import co.edu.unicauca.microcoordinador.infra.config.RabbitMQConfig;
import co.edu.unicauca.microcoordinador.infra.dto.CoordinadorDto;
import co.edu.unicauca.microcoordinador.services.impl.CoordinadorService;
import co.edu.unicauca.microcoordinador.services.interfaces.ICoordinadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioListener {
    private final CoordinadorService coordinadorService;

    @RabbitListener(queues = "coordinadores_queue")
    public void recibirCoordinador(CoordinadorDto coordinadorDto) {
        log.info("Mensaje recibido en la cola: {}", coordinadorDto);
        try {
            log.debug("Procesando CoordinadorDto con los siguientes valores: idCoordinador={}, nombre={}, apellido={}, email={}, telefono={}",
                    coordinadorDto.getIdCoordinador(), coordinadorDto.getNombreCoordinador(), coordinadorDto.getApellidoCoordinador(),
                    coordinadorDto.getEmailCoordinador(), coordinadorDto.getTelefonoCoordinador());
           coordinadorService.guardarCoordinadorDesdeCola(coordinadorDto);//revisar el metodo guardardesdelacola
            System.out.println("Coordinador recibido y procesado: " + coordinadorDto.getNombreCoordinador());
        } catch (Exception e) {
            System.err.println("Error al procesar coordinador: " + e.getMessage());
            throw new AmqpRejectAndDontRequeueException(e);  // No reintentar el mensaje en caso de error
        }
    }
}
