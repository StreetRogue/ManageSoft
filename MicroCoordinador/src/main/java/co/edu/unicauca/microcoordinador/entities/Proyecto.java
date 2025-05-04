/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.microcoordinador.entities;

import co.edu.unicauca.microcoordinador.entities.state.EstadoProyecto;
import co.edu.unicauca.microcoordinador.entities.state.EstadoProyectoFactory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "estadoProyecto")
    private EnumEstadoProyecto estadoProyecto;

    @Column(name = "comentario")
    private String comentario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coordinador_id")
    private Coordinador coordinador;

    @Transient
    private EstadoProyecto estadoActual;

    @PostLoad
    private void cargarEstado() {
        this.estadoActual = EstadoProyectoFactory.obtenerEstado(this.estadoProyecto);
    }

    public void cambiarEstado(EnumEstadoProyecto nuevoEstado, String comentario) {
        EstadoProyecto estadoNuevo = EstadoProyectoFactory.obtenerEstado(nuevoEstado);
        this.estadoProyecto = estadoNuevo.getNombreEstado(); // persistente
        this.estadoActual = estadoNuevo;                     // lógico
        this.comentario = comentario;
    }

}
