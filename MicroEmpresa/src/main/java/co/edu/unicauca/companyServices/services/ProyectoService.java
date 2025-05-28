package co.edu.unicauca.companyServices.services;

import co.edu.unicauca.companyServices.dtos.ProyectoDTO;
import co.edu.unicauca.companyServices.dtos.ProyectoDetalleDTO;
import co.edu.unicauca.companyServices.entities.Empresa;
import co.edu.unicauca.companyServices.entities.EstadoProyecto;
import co.edu.unicauca.companyServices.entities.HistorialProyecto;
import co.edu.unicauca.companyServices.entities.Proyecto;
import co.edu.unicauca.companyServices.mappers.ProyectoMapper;
import co.edu.unicauca.companyServices.repositories.HistorialProyectoRepository;
import co.edu.unicauca.companyServices.repositories.ProyectoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private HistorialProyectoRepository historialProyectoRepository;

    @Autowired
    private final ProyectoMapper proyectoMapper;

    public List<Proyecto> listarProyectos() {
        return proyectoRepository.findAll();
    }

    public Proyecto findById(Long id) {
        return proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
    }

    /*
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
    */

    @Transactional
    public Proyecto actualizarEstado(Long id, EstadoProyecto nuevoEstado) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        proyecto.setEstadoProyecto(nuevoEstado);
        Proyecto proyectoActualizado = proyectoRepository.save(proyecto);

        // ✅ Registrar en historial
        HistorialProyecto historial = new HistorialProyecto(proyecto, nuevoEstado);
        historialProyectoRepository.save(historial);

        // Opcional: enviar por RabbitMQ, etc.
        if (nuevoEstado == EstadoProyecto.ACEPTADO ||
                nuevoEstado == EstadoProyecto.RECHAZADO ||
                nuevoEstado == EstadoProyecto.EN_EJECUCION ||
                nuevoEstado == EstadoProyecto.CERRADO) {

            ProyectoDTO dto = proyectoMapper.toProyectoDTO(proyectoActualizado);
            // Aquí iría el envío por Rabbit si aplica
        }

        return proyectoActualizado;
    }

    public long contarProyectosPorEstado(EstadoProyecto estadoProyecto) {
        return proyectoRepository.countByEstadoProyecto(estadoProyecto);
    }

    public long contarPorEstadoYPeriodo(String estado, String periodoAcademico) {
        EstadoProyecto estadoEnum;
        try {
            estadoEnum = EstadoProyecto.valueOf(estado.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Estado no válido");
        }

        String[] partes = periodoAcademico.split("-");
        if (partes.length != 2) throw new RuntimeException("Formato de periodo académico inválido. Usa AAAA-1 o AAAA-2");

        int anio = Integer.parseInt(partes[0]);
        int semestre = Integer.parseInt(partes[1]);

        LocalDate inicio, fin;
        if (semestre == 1) {
            inicio = LocalDate.of(anio, 2, 1);
            fin = LocalDate.of(anio, 6, 30);
        } else {
            inicio = LocalDate.of(anio, 7, 1);
            fin = LocalDate.of(anio, 11, 20);
        }

        return proyectoRepository.contarPorEstadoYPeriodo(estadoEnum, inicio, fin);
    }

    public Integer obtenerPromedioDiasAceptacion(String periodo) {
        LocalDate inicio;
        LocalDate fin;

        if (periodo.endsWith("-1")) {
            inicio = LocalDate.parse(periodo.substring(0, 4) + "-01-01");
            fin = LocalDate.parse(periodo.substring(0, 4) + "-06-30");
        } else if (periodo.endsWith("-2")) {
            inicio = LocalDate.parse(periodo.substring(0, 4) + "-07-01");
            fin = LocalDate.parse(periodo.substring(0, 4) + "-12-31");
        } else {
            throw new IllegalArgumentException("Formato de periodo inválido: " + periodo);
        }

        return proyectoRepository.promedioDiasAceptado(inicio, fin);
    }

    public ProyectoDetalleDTO obtenerDetalleProyecto(Long id) {
        Proyecto proyecto = proyectoRepository.findWithEmpresaAndHistorialByIdProyecto(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con ID: " + id));

        ProyectoDetalleDTO dto = new ProyectoDetalleDTO();
        dto.setIdProyecto(proyecto.getIdProyecto());
        dto.setNombreProyecto(proyecto.getNombreProyecto());
        dto.setResumenProyecto(proyecto.getResumenProyecto());
        dto.setObjetivoProyecto(proyecto.getObjetivoProyecto());
        dto.setDescripcionProyecto(proyecto.getDescripcionProyecto());
        dto.setMaximoMesesProyecto(proyecto.getMaximoMesesProyecto());
        dto.setPresupuestoProyecto(proyecto.getPresupuestoProyecto());
        dto.setFechaPublicacionProyecto(proyecto.getFechaPublicacionProyecto());
        dto.setEstadoProyecto(proyecto.getEstadoProyecto());

        // Empresa
        Empresa empresa = proyecto.getEmpresa();
        if (empresa != null) {
            ProyectoDetalleDTO.EmpresaDTO empresaDTO = new ProyectoDetalleDTO.EmpresaDTO(
                    empresa.getNitEmpresa(),
                    empresa.getNombreEmpresa(),
                    empresa.getEmailEmpresa(),
                    empresa.getSectorEmpresa(),
                    empresa.getContactoEmpresa(),
                    empresa.getNombreContactoEmpresa(),
                    empresa.getApellidoContactoEmpresa(),
                    empresa.getCargoContactoEmpresa()
            );
            dto.setEmpresa(empresaDTO);
        }

        // Historial
        List<ProyectoDetalleDTO.HistorialDTO> historialDTOs = proyecto.getHistorial().stream()
                .map(h -> new ProyectoDetalleDTO.HistorialDTO(h.getEstado()))
                .toList();
        dto.setHistorial(historialDTOs);

        return dto;
    }
}

