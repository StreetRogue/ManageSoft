package co.edu.unicauca.companyServices.dtos;

import co.edu.unicauca.companyServices.entities.EstadoProyecto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistorialProyectoDTO {
    private EstadoProyecto estado;
    private LocalDateTime fechaCambio;
}
