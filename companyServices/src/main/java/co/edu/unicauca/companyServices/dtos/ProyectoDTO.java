package co.edu.unicauca.companyServices.dtos;

import lombok.Data;
import java.time.LocalDate;
import java.util.Date;

@Data
public class ProyectoDTO {
    private Long id;
    private String nombre;
    private String resumen;
    private String objetivo;
    private String descripcion;
    private String duracionMeses;
    private String presupuesto;
    private LocalDate fechaPublicacion;
    private String estado;
    private EmpresaBasicDTO empresa;
}
