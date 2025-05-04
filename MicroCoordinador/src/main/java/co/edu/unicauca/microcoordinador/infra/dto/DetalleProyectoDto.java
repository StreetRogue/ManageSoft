package co.edu.unicauca.microcoordinador.infra.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetalleProyectoDto {
    private Long id;
    private String nombre;
    private String resumen;
    private String descripcion;
    private String objetivo;
    private String estado;
    private String presupuesto;
    private String maximoMeses;
    private String fechaPublicacion;
    private String comentario;
    private String nombreEmpresa;
    private String nombreCoordinador;
}
