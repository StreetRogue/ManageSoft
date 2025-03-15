/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.services;

import co.edu.unicauca.managesoft.access.IUsuarioRepositorio;

/**
 *
 * @author jutak
 */
public class LoginServices {
    private IUsuarioRepositorio repositorio;

    public LoginServices(IUsuarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }
    
    public boolean iniciarSesion(String nombreUsuario, String contrasenaUsuario) {
        return repositorio.autenticarInicioSesion(nombreUsuario, contrasenaUsuario);
    }
}
