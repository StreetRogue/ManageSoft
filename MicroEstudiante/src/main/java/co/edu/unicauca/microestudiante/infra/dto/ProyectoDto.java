package co.edu.unicauca.microestudiante.infra.dto;

import co.edu.unicauca.microestudiante.entities.EstadoProyecto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProyectoDto {
    private Long idProyecto;
    private String nombreProyecto;
    private String resumenProyecto;
    private String objetivoProyecto;
    private String descripcionProyecto;
    private String maximoMesesProyecto;
    private String presupuestoProyecto;
    private String fechaPublicacionProyecto;
    private EstadoProyecto estadoProyecto;
    private List<Long> estudiantesPostulados;
    private List<Long> estudiantesAceptados;
}
