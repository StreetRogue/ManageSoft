/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.entities;

import java.util.List;

/**
 *
 * @author jutak
 */
public class Estudiante extends Usuario {
    private String nombreEstudiante;
    private String apellidoEstudiante;
    private String codigoSimcaEstudiante;
    private String emailEstudiante;
    private List<Proyecto> proyectosPostulados;
    private List<Proyecto> proyectosAceptados;

    public Estudiante(String nombreEstudiante, String apellidoEstudiante, String codigoSimcaEstudiante, String emailEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
        this.apellidoEstudiante = apellidoEstudiante;
        this.codigoSimcaEstudiante = codigoSimcaEstudiante;
        this.emailEstudiante = emailEstudiante;
    }

    public Estudiante(String nombreEstudiante, String apellidoEstudiante, String codigoSimcaEstudiante, String emailEstudiante, String nombreUsuario, String contrasenaUsuario) {
        super(nombreUsuario, contrasenaUsuario, enumTipoUsuario.ESTUDIANTE);
        this.nombreEstudiante = nombreEstudiante;
        this.apellidoEstudiante = apellidoEstudiante;
        this.codigoSimcaEstudiante = codigoSimcaEstudiante;
        this.emailEstudiante = emailEstudiante;
    }
    
    public Estudiante(){}

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public String getApellidoEstudiante() {
        return apellidoEstudiante;
    }

    public void setApellidoEstudiante(String apellidoEstudiante) {
        this.apellidoEstudiante = apellidoEstudiante;
    }

    public String getCodigoSimcaEstudiante() {
        return codigoSimcaEstudiante;
    }

    public void setCodigoSimcaEstudiante(String codigoSimcaEstudiante) {
        this.codigoSimcaEstudiante = codigoSimcaEstudiante;
    }

    public String getEmailEstudiante() {
        return emailEstudiante;
    }

    public void setEmailEstudiante(String emailEstudiante) {
        this.emailEstudiante = emailEstudiante;
    }

    public List<Proyecto> getProyectosPostulados() {
        return proyectosPostulados;
    }

    public void setProyectosPostulados(List<Proyecto> proyectosPostulados) {
        this.proyectosPostulados = proyectosPostulados;
    }

    public List<Proyecto> getProyectosAceptados() {
        return proyectosAceptados;
    }

    public void setProyectosAceptados(List<Proyecto> proyectosAceptados) {
        this.proyectosAceptados = proyectosAceptados;
    }
}
