package co.edu.unicauca.microcoordinador.infra.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProyectoResumenDto {
    private Long id;
    private String nombre;
    private String estado;
}
