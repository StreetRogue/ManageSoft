/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.microestudiante.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "proyecto")
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProyecto")
    private Long idProyecto;
    @Column(name = "nombreProyecto")
    private String nombreProyecto;
    @Column(name = "resumenProyecto")
    private String resumenProyecto;
    @Column(name = "objetivoProyecto")
    private String objetivoProyecto;
    @Column(name = "descripcionProyecto")
    private String descripcionProyecto;
    @Column(name = "maximoMesesProyecto")
    private String maximoMesesProyecto;
    @Column(name = "presupuestoProyecto")
    private String presupuestoProyecto;
    @Column(name = "fechaPublicacionProyecto")
    private String fechaPublicacionProyecto;
    @Column(name = "estadoProyecto")
    private enumEstadoProyecto estadoProyecto;

    @ManyToMany
    @JoinTable(
            name = "estudiantesPostulados",
            joinColumns = @JoinColumn(name = "idProyecto"),
            inverseJoinColumns = @JoinColumn(name = "codigoSimcaEstudiante")
    )
    private List<Estudiante> estudiantesPostulados = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "estudiantesAceptados",
            joinColumns = @JoinColumn(name = "idProyecto"),
            inverseJoinColumns = @JoinColumn(name = "codigoSimcaEstudiante")
    )
    private List<Estudiante> estudiantesAceptados = new ArrayList<>();
}
