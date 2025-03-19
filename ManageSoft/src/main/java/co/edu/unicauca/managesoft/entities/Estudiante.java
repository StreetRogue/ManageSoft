/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.entities;

/**
 *
 * @author jutak
 */
public class Estudiante extends Usuario {
    private String nombreEstudiante;
    private String apellidoEstudiante;
    private String codigoSimcaEstudiante;
    private String emailEstudiante;
    private int idUsuario;

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

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    
    
}
