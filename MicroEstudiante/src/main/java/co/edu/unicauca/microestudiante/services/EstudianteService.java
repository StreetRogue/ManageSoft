package co.edu.unicauca.microestudiante.services;

import co.edu.unicauca.microestudiante.access.IEstudianteRepositorio;
import co.edu.unicauca.microestudiante.entities.enumEstadoProyecto;
import co.edu.unicauca.microestudiante.infra.dto.EstudianteDto;
import jakarta.transaction.Transactional;

import co.edu.unicauca.microestudiante.entities.Proyecto;
import co.edu.unicauca.microestudiante.entities.Estudiante;
import co.edu.unicauca.microestudiante.access.IProyectoRepositorio;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import co.edu.unicauca.microestudiante.infra.config.RabbitMQConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class EstudianteService {
    private final ProyectoService proyectoService;
    private final IEstudianteRepositorio estudianteRepositorio;
    private final IProyectoRepositorio proyectoRepositorio;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public EstudianteService(ProyectoService proyectoService, IEstudianteRepositorio estudianteRepositorio, IProyectoRepositorio proyectoRepositorio) {
        this.proyectoService = proyectoService;
        this.estudianteRepositorio = estudianteRepositorio;
        this.proyectoRepositorio = proyectoRepositorio;
    }

    @Transactional
    public boolean postulacion(Long idEstudiante, Long idProyecto) throws Exception {
        try{
            Proyecto proyecto;
            Estudiante estudiante;

            if (proyectoRepositorio.existsById(idProyecto) || (estudianteRepositorio.existsById(idEstudiante))) {
                proyecto = proyectoRepositorio.findById(idProyecto).get();
                estudiante = estudianteRepositorio.findById(idEstudiante).get();

                if (!estudiante.getProyectosPostulados().contains(proyecto)) {
                    if (proyecto.getEstadoProyecto().equals(enumEstadoProyecto.ACEPTADO)) {
                        estudiante.postularse(proyecto);
                        proyecto.getEstudiantesPostulados().add(estudiante);

                        estudianteRepositorio.save(estudiante);
                        proyectoRepositorio.save(proyecto);
                        EstudianteDto estudianteDto = new EstudianteDto(estudiante.getCodigoSimcaEstudiante(), estudiante.getNombreEstudiante(), estudiante.getApellidoEstudiante(), estudiante.getEmailEstudiante());
                        estudianteDto.setProyectosPostulados(estudiante.getProyectosPostulados().stream().map(Proyecto::getIdProyecto).collect(Collectors.toList()));
                        estudianteDto.setProyectoAceptados(estudiante.getProyectoAceptados().stream().map(Proyecto::getIdProyecto).collect(Collectors.toList()));
                        rabbitTemplate.convertAndSend(RabbitMQConfig.COLA_POSTULACION,estudianteDto);
                        rabbitTemplate.convertAndSend(RabbitMQConfig.COLA_NOTIFIACIONCOORDINADOR,estudianteDto);
                        return true;
                    } else {
                        throw new Exception("El proyecto no tiene el estado aceptado");
                    }
                } else {
                    throw new Exception("Estudiante ya postulado");
                }
            }
            return false;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
