package co.edu.unicauca.microestudiante.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "estudiante")
public class Estudiante {
    @Id
    @Column(name = "codigoSimcaEstudiante")
    private Long codigoSimcaEstudiante;
    @Column(name = "nombreEstudiante")
    private String nombreEstudiante;
    @Column(name = "apellidoEstudiante")
    private String apellidoEstudiante;
    @Column(name = "emailEstudiante")
    private String emailEstudiante;
    @ManyToMany(mappedBy = "estudiantesPostulados")
    private List<Proyecto> proyectosPostulados;
    @ManyToMany(mappedBy = "estudiantesAceptados")
    private List<Proyecto> proyectoAceptados;

    public Estudiante(Long codigoSimcaEstudiante, String nombreEstudiante, String apellidoEstudiante, String emailEstudiante) {
        this.codigoSimcaEstudiante = codigoSimcaEstudiante;
        this.nombreEstudiante = nombreEstudiante;
        this.apellidoEstudiante = apellidoEstudiante;
        this.emailEstudiante = emailEstudiante;
        this.proyectosPostulados = new ArrayList<>();
        this.proyectoAceptados = new ArrayList<>();
    }


    public Estudiante() {

    }

    public void postularse(Proyecto proyecto) {
        proyectosPostulados.add(proyecto);
    }
}
