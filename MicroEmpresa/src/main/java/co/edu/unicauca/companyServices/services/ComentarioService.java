package co.edu.unicauca.companyServices.services;

import co.edu.unicauca.companyServices.repositories.ComentarioRepository;
import co.edu.unicauca.companyServices.entities.Comentario;
import co.edu.unicauca.companyServices.dtos.ComentarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;

    @RabbitListener(queues = "cola.comentarioEmpresa")
    public void guardarComentario(ComentarioDTO dto) {
        Comentario comentario = Comentario.builder()
                .comentario(dto.getComentario())
                .emailCoordinador(dto.getEmailCoordinador())
                .emailEmpresa(dto.getEmailEmpresa())
                .idProyecto(dto.getIdProyecto())
                .build();

        Comentario comentarioGuardado = comentarioRepository.save(comentario);

    }

    public List<Comentario> mostrarComentarios() {
        return comentarioRepository.findAll();
    }

    public int contarComentariosPorEmailCoordinador(String email) {
        return comentarioRepository.countByEmailCoordinador(email);
    }
}
