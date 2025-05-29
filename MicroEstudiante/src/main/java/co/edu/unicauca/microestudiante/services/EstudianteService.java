package co.edu.unicauca.microestudiante.services;

import co.edu.unicauca.microestudiante.access.IEstudianteRepositorio;
import co.edu.unicauca.microestudiante.entities.EstadoProyecto;
import co.edu.unicauca.microestudiante.infra.dto.EstudianteDto;
import jakarta.transaction.Transactional;

import co.edu.unicauca.microestudiante.entities.Proyecto;
import co.edu.unicauca.microestudiante.entities.Estudiante;
import co.edu.unicauca.microestudiante.access.IProyectoRepositorio;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import co.edu.unicauca.microestudiante.infra.config.RabbitMQConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    @RabbitListener(queues = RabbitMQConfig.COLA_ESTUDIANTESREGISTRADOS)
    public void guardarEstudianteCola(EstudianteDto estudianteDto) {
        Estudiante estudiante = new Estudiante(estudianteDto.getNombreUsuario(),
                estudianteDto.getContrasenaUsuario(), estudianteDto.getCodigoSimcaEstudiante(),
                estudianteDto.getNombreEstudiante(), estudianteDto.getApellidoEstudiante(),
                estudianteDto.getEmailEstudiante());

        if (estudianteDto.getProyectosPostulados() != null && !estudianteDto.getProyectosPostulados().isEmpty()) {
            estudiante.setProyectosPostulados(proyectoRepositorio.findAllById(estudianteDto.getProyectosPostulados()));
        }
        if (estudianteDto.getProyectosAceptados() != null && !estudianteDto.getProyectosAceptados().isEmpty()) {
            estudiante.setProyectoAceptados(proyectoRepositorio.findAllById(estudianteDto.getProyectosAceptados()));
        }

        estudianteRepositorio.save(estudiante);
    }

    public Estudiante registrarEstudiante(Estudiante estudiante) throws Exception {
        if (estudianteRepositorio.existsSimca(estudiante.getCodigoSimcaEstudiante())) {
            throw new Exception("Ya existe un estudiante con el mismo Simca: " + estudiante.getCodigoSimcaEstudiante());
        }

        // Verificar si ya existe una empresa con el mismo email
        if (estudianteRepositorio.existsEmail(estudiante.getEmailEstudiante())) {
            throw new Exception("Ya existe un estudiante con el mismo email: " + estudiante.getEmailEstudiante());
        }

        // Verificar si ya existe una empresa con el mismo username
        if (estudianteRepositorio.existsUser(estudiante.getNombreUsuario())) {
            throw new Exception("Ya existe un estudiante con el mismo nombre de usuario: " + estudiante.getNombreUsuario());
        }

        // Si no hay conflictos, guardar la empresa
        return estudianteRepositorio.save(estudiante);
    }

    @Transactional
    public EstudianteDto buscarEstudiante(String nombreUsuario, String contrasena) throws Exception {
        Optional<Estudiante> estudiante = estudianteRepositorio.findByUsernameAndPassword(nombreUsuario, contrasena);
        if (estudiante.isEmpty()) {throw new Exception("El estudiante no se ha encontrado");}
        return convertirADto(estudiante.get());
    }

    @Transactional
    public List<EstudianteDto> listarEstudiantes() {
        List<EstudianteDto> estudiantesDto = estudianteRepositorio.findAll().stream().map(this::convertirADto).collect(Collectors.toList());
        return estudiantesDto;
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
                    if (proyecto.getEstadoProyecto().equals(EstadoProyecto.ACEPTADO)) {
                        estudiante.postularse(proyecto);
                        proyecto.getEstudiantesPostulados().add(estudiante);

                        estudianteRepositorio.save(estudiante);
                        proyectoRepositorio.save(proyecto);
                        EstudianteDto estudianteDto = convertirADto(estudiante);
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
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    private EstudianteDto convertirADto(Estudiante estudiante) {
        EstudianteDto estudianteDto = new EstudianteDto(estudiante.getNombreUsuario(), estudiante.getContrasenaUsuario(), estudiante.getCodigoSimcaEstudiante(), estudiante.getNombreEstudiante(), estudiante.getApellidoEstudiante(), estudiante.getEmailEstudiante());
        estudianteDto.setProyectosPostulados(estudiante.getProyectosPostulados().stream().map(Proyecto::getIdProyecto).collect(Collectors.toList()));
        estudianteDto.setProyectosAceptados(estudiante.getProyectoAceptados().stream().map(Proyecto::getIdProyecto).collect(Collectors.toList()));
        return estudianteDto;
    }

    


    public Integer contarEstudiantes() {
        return estudianteRepositorio.contarEstudiantes();
    }
}
