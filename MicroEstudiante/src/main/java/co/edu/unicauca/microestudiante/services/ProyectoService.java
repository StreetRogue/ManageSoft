package co.edu.unicauca.microestudiante.services;

import co.edu.unicauca.microestudiante.access.IEstudianteRepositorio;
import co.edu.unicauca.microestudiante.access.IProyectoRepositorio;
import co.edu.unicauca.microestudiante.entities.Proyecto;
import co.edu.unicauca.microestudiante.infra.config.RabbitMQConfig;
import co.edu.unicauca.microestudiante.infra.dto.ProyectoDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProyectoService {
    @Autowired
    private IProyectoRepositorio proyectoRepositorio;
    @Autowired
    private IEstudianteRepositorio estudianteRepositorio;

    @RabbitListener(queues = RabbitMQConfig.COLA_PROYECTOS)
    public void guardarProyecto(ProyectoDto proyectoDto) {
        Proyecto proyecto = new Proyecto(proyectoDto.getIdProyecto(), proyectoDto.getNombreProyecto(),
                proyectoDto.getResumenProyecto(), proyectoDto.getMaximoMesesProyecto(), proyectoDto.getFechaPublicacionProyecto(),
                proyectoDto.getPresupuestoProyecto(), proyectoDto.getDescripcionProyecto(), proyectoDto.getObjetivoProyecto(),
                proyectoDto.getEstadoProyecto());
        proyecto.setEstudiantesPostulados(estudianteRepositorio.findAllById(proyectoDto.getEstudiantesPostulados()));
        proyecto.setEstudiantesAceptados(estudianteRepositorio.findAllById(proyectoDto.getEstudiantesAceptados()));
        proyectoRepositorio.save(proyecto);
    }
}
