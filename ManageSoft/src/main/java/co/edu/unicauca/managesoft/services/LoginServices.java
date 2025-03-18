/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.services;

import co.edu.unicauca.managesoft.access.IUsuarioRepositorio;
import co.edu.unicauca.managesoft.entities.Usuario;
import co.edu.unicauca.managesoft.entities.enumTipoUsuario;

/**
 *
 * @author jutak
 */
public class LogInServices {
    private IUsuarioRepositorio repositorio;

    public LogInServices(IUsuarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }
        
    public Usuario iniciarSesion(String nombreUsuario, String contrasenaUsuario) {
        Usuario usuarioInicioSesion = repositorio.iniciarSesion(nombreUsuario, contrasenaUsuario);
        return usuarioInicioSesion;
    }
    
    public boolean registrarUsuario(String nombreUsuario, String contrasenaUsuario, enumTipoUsuario tipoUsuario) {
        Usuario nuevoUsuario = new Usuario(nombreUsuario, contrasenaUsuario, tipoUsuario);
        LogInValidation nuevaValidacion = new LogInValidation(nuevoUsuario);
        nuevaValidacion.camposNoVacios();
        return repositorio.registrarUsuario(nuevoUsuario);
    }
}
