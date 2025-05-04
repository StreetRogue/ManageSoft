package co.edu.unicauca.microcoordinador.services.impl;
import co.edu.unicauca.microcoordinador.access.repositories.ICoordinadorRepositorio;
import co.edu.unicauca.microcoordinador.access.repositories.IProyectoRepositorio;
import co.edu.unicauca.microcoordinador.entities.Coordinador;
import co.edu.unicauca.microcoordinador.entities.EnumEstadoProyecto;
import co.edu.unicauca.microcoordinador.entities.Proyecto;
import co.edu.unicauca.microcoordinador.infra.dto.CambioEstadoDto;
import co.edu.unicauca.microcoordinador.infra.dto.DetalleProyectoDto;
import co.edu.unicauca.microcoordinador.infra.dto.ProyectoResumenDto;
import co.edu.unicauca.microcoordinador.services.interfaces.ICoordinadorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoordinadorService implements ICoordinadorService {

    private final ICoordinadorRepositorio coordinadorRepositorio;
    private final IProyectoRepositorio proyectoRepositorio;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public List<ProyectoResumenDto> obtenerProyectosDelCoordinador(Long idCoordinador) {
        Coordinador coordinador = coordinadorRepositorio.findById(idCoordinador)
                .orElseThrow(() -> new EntityNotFoundException("Coordinador no encontrado"));

        return coordinador.getProyectosAsignados().stream()
                .map(proyecto -> new ProyectoResumenDto(
                        proyecto.getIdProyecto(),
                        proyecto.getNombreProyecto(),
                        proyecto.getEstadoProyecto().name()))
                .collect(Collectors.toList());
    }

    @Override
    public DetalleProyectoDto obtenerDetalleProyecto(Long idProyecto) {
        Proyecto proyecto = proyectoRepositorio.findById(idProyecto)
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado"));

        return new DetalleProyectoDto(
                proyecto.getIdProyecto(),
                proyecto.getNombreProyecto(),
                proyecto.getResumenProyecto(),
                proyecto.getDescripcionProyecto(),
                proyecto.getObjetivoProyecto(),
                proyecto.getEstadoProyecto().name(),
                proyecto.getPresupuestoProyecto(),
                proyecto.getMaximoMesesProyecto(),
                proyecto.getFechaPublicacionProyecto(),
                proyecto.getComentario(),
                proyecto.getEmpresa().getNombreEmpresa(),
                proyecto.getCoordinador().getNombre()
        );
    }

    @Override
    public void cambiarEstadoProyecto(Long idProyecto, CambioEstadoDto cambioEstadoDto) {
        Proyecto proyecto = proyectoRepositorio.findById(idProyecto)
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado"));

        EnumEstadoProyecto nuevoEstado = EnumEstadoProyecto.valueOf(cambioEstadoDto.getNuevoEstado());
        proyecto.cambiarEstado(nuevoEstado, cambioEstadoDto.getComentario());
        proyectoRepositorio.save(proyecto);

        // Notificar a la cola (opcional)
        rabbitTemplate.convertAndSend("ColaNotificacionCoordinador", "Proyecto " + proyecto.getNombreProyecto() +
                " cambió a estado: " + nuevoEstado.name());
    }

    @Override
    public Coordinador guardarCoordinador(Coordinador coordinador) {
        return coordinadorRepositorio.save(coordinador);
    }
}
