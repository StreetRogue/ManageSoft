package co.edu.unicauca.microestudiante.infra.dto;


import co.edu.unicauca.microestudiante.entities.TipoUsuario;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EstudianteDto {
    private String nombreUsuario;
    private String contrasenaUsuario;
    private TipoUsuario tipoUsuario;
    private Long codigoSimcaEstudiante;
    private String nombreEstudiante;
    private String apellidoEstudiante;
    private String emailEstudiante;
    private List<Long> proyectosPostulados;
    private List<Long> proyectosAceptados;

    public EstudianteDto(String nombreUsuario, String contrasenaUsuario, Long codigoSimcaEstudiante, String nombreEstudiante, String apellidoEstudiante, String emailEstudiante) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenaUsuario = contrasenaUsuario;
        this.tipoUsuario = TipoUsuario.ESTUDIANTE;
        this.codigoSimcaEstudiante = codigoSimcaEstudiante;
        this.nombreEstudiante = nombreEstudiante;
        this.apellidoEstudiante = apellidoEstudiante;
        this.emailEstudiante = emailEstudiante;
        this.proyectosPostulados = new ArrayList<>();
        this.proyectosAceptados = new ArrayList<>();
    }
}
