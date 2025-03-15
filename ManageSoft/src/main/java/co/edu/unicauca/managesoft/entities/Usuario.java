/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.entities;

/**
 *
 * @author jutak
 */
public class Usuario {
    private String nombreUsuario;
    private String contrasenaUsuario;
    private enumTipoUsuario tipoUsuario;

    public Usuario(String nombreUsuario, String contrasenaUsuario, enumTipoUsuario tipoUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenaUsuario = contrasenaUsuario;
        this.tipoUsuario = tipoUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenaUsuario() {
        return contrasenaUsuario;
    }

    public void setContrasenaUsuario(String contrasenaUsuario) {
        this.contrasenaUsuario = contrasenaUsuario;
    }

    public enumTipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(enumTipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    
    
}
