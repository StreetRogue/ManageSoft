package co.edu.unicauca.microestudiante.infra.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EstudianteDto {
    private Long codigoSimcaEstudiante;
    private String nombreEstudiante;
    private String apellidoEstudiante;
    private String emailEstudiante;
    private List<Long> proyectosPostulados;
    private List<Long> proyectoAceptados;

    public EstudianteDto(Long codigoSimcaEstudiante, String nombreEstudiante, String apellidoEstudiante, String emailEstudiante) {
        this.codigoSimcaEstudiante = codigoSimcaEstudiante;
        this.nombreEstudiante = nombreEstudiante;
        this.apellidoEstudiante = apellidoEstudiante;
        this.emailEstudiante = emailEstudiante;
        this.proyectosPostulados = new ArrayList<>();
        this.proyectoAceptados = new ArrayList<>();
    }
}
