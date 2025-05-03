package co.edu.unicauca.MicroNotification.services;

import co.edu.unicauca.MicroNotification.access.ComentarioRepository;
import co.edu.unicauca.MicroNotification.entities.Comentario;
import co.edu.unicauca.MicroNotification.infra.dto.ComentarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Service
@RequiredArgsConstructor
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "cola.comentario")
    public void guardarComentario(ComentarioDTO dto) {
        Comentario comentario = Comentario.builder()
                .comentario(dto.getComentario())
                .emailCoordinador(dto.getEmailCoordinador())
                .emailEmpresa(dto.getEmailEmpresa())
                .idProyecto(dto.getIdProyecto())
                .build();

        Comentario comentarioGuardado = comentarioRepository.save(comentario);

        rabbitTemplate.convertAndSend("cola.comentarioEmpresa", comentarioGuardado);
    }
}
