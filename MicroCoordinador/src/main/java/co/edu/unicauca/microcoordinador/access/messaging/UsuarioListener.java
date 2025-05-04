package co.edu.unicauca.microcoordinador.access.messaging;

import co.edu.unicauca.microcoordinador.entities.Coordinador;
import co.edu.unicauca.microcoordinador.infra.config.RabbitMQConfig;
import co.edu.unicauca.microcoordinador.infra.dto.CoordinadorDto;
import co.edu.unicauca.microcoordinador.services.interfaces.ICoordinadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioListener {

    private static ICoordinadorService coordinadorService;

    @RabbitListener(queues = RabbitMQConfig.COLA_USUARIO_COORDINADOR)
    public void recibirMensaje(CoordinadorDto dto) {
        Coordinador coordinador = new Coordinador(
                dto.getCodigoSimca(),
                dto.getNombre(),
                dto.getApellido(),
                dto.getEmail(),
                dto.getTelefono(),
                dto.getContrasena()
        );
        coordinadorService.guardarCoordinador(coordinador);
        System.out.println("Coordinador recibido y guardado: " + dto.getEmail());
    }
}
