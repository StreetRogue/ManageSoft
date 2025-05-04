package co.edu.unicauca.microcoordinador.infra.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoordinadorDto {
    private String codigoSimca;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String contrasena;
}
