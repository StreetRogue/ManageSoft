package co.edu.unicauca.microcoordinador.infra.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoordinadorDto {
    private Long idUsuario;
    private String nombreUsuario;
    private String contrasenaUsuario;
    private String tipoUsuario;
    private Long idCoordinador;
    private String nombreCoordinador;
    private String apellidoCoordinador;
    private String emailCoordinador;
    private String telefonoCoordinador;
}


/*
Usuario enviado a la cola coordinadores_queue: {"idUsuario":1,"nombreUsuario":"coord1","contrasenaUsuario":"clavesita123","tipoUsuario":"COORDINADOR","idCoordinador":1,"nombreCoordinador":"Juan","apellidoCoordinador":"Pérez","emailCoordinador":"juan.perez@unicauca.edu.com","telefonoCoordinador":"3001234564"}
Usuario enviado a la cola: Coordinador(idCoordinador=1, nombreCoordinador=Juan, apellidoCoordinador=Pérez, emailCoordinador=juan.perez@unicauca.edu.com, telefonoCoordinador=3001234564)
*/