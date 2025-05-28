package co.edu.unicauca.companyServices.dtos;
import co.edu.unicauca.companyServices.entities.EstadoProyecto;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ProyectoDetalleDTO {
    private Long idProyecto;
    private String nombreProyecto;
    private String resumenProyecto;
    private String objetivoProyecto;
    private String descripcionProyecto;
    private String maximoMesesProyecto;
    private String presupuestoProyecto;
    private LocalDate fechaPublicacionProyecto;
    private EstadoProyecto estadoProyecto;

    private EmpresaDTO empresa;
    private List<HistorialDTO> historial;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class EmpresaDTO {
        private String nitEmpresa;
        private String nombreEmpresa;
        private String emailEmpresa;
        private String sectorEmpresa;
        private String contactoEmpresa;
        private String nombreContactoEmpresa;
        private String apellidoContactoEmpresa;
        private String cargoContactoEmpresa;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class HistorialDTO {
        private EstadoProyecto estado;
    }
}

