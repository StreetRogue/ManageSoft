package co.edu.unicauca.companyServices.services;

import co.edu.unicauca.companyServices.dtos.ProyectoDTO;
import co.edu.unicauca.companyServices.entities.Empresa;
import co.edu.unicauca.companyServices.entities.EstadoProyecto;
import co.edu.unicauca.companyServices.entities.Proyecto;
import co.edu.unicauca.companyServices.mappers.ProyectoMapper;
import co.edu.unicauca.companyServices.repositories.ProyectoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;
    private final RabbitMQProducerService rabbitProducer;
    private final ProyectoMapper proyectoMapper;

    public List<Proyecto> listarProyectos() {
        return proyectoRepository.findAll();
    }

    public Proyecto findById(Long id) {
        return proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
    }

    @Transactional
    public Proyecto actualizarEstado(Long id, EstadoProyecto nuevoEstado) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        proyecto.setEstadoProyecto(nuevoEstado);
        Proyecto proyectoActualizado = proyectoRepository.save(proyecto);

        // Enviar a RabbitMQ cuando el estado cambia
        if (nuevoEstado == EstadoProyecto.ACEPTADO || nuevoEstado == EstadoProyecto.RECHAZADO ||  nuevoEstado == EstadoProyecto.EN_EJECUCION ||  nuevoEstado == EstadoProyecto.CERRADO ) {
            ProyectoDTO dto = proyectoMapper.toProyectoDTO(proyectoActualizado);

        }

        return proyectoActualizado;
    }
}

