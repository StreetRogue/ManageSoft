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
public class Coordinador extends Usuario {
    private String nombre;
    private String email;
    private String telefono;

    // Constructor solo con nombreUsuario y contrasenaUsuario, heredados de Usuario
    public Coordinador(String nombreUsuario, String contrasenaUsuario) {
        super(nombreUsuario, contrasenaUsuario, enumTipoUsuario.COORDINADOR); // Llamada al constructor de la clase padre
    }

    // Constructor con todos los datos
    public Coordinador(String nombreUsuario, String contrasenaUsuario, String nombre, String email, String telefono) {
        super(nombreUsuario, contrasenaUsuario, enumTipoUsuario.COORDINADOR);
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}

