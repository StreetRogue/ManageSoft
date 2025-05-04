package co.edu.unicauca.microcoordinador.infra.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//Se usa en el POST para cambiar el estado de un proyecto
public class CambioEstadoDto {
    private String nuevoEstado;
    private String comentario;
}

