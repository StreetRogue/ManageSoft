/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.services;

import co.edu.unicauca.managesoft.access.IUsuarioRepositorio;
import co.edu.unicauca.managesoft.entities.Empresa;
import co.edu.unicauca.managesoft.entities.Estudiante;
import co.edu.unicauca.managesoft.entities.Usuario;
import co.edu.unicauca.managesoft.entities.enumTipoUsuario;
import co.edu.unicauca.managesoft.infra.MyException;

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
    
    public boolean guardarEmpresa(Empresa empresa) throws MyException {
       LogInValidation nuevaValidacion = new LogInValidation(empresa);
       try {
           nuevaValidacion.camposEmpresa();
           return true;
       } catch(MyException e) {
           throw e;
       }
    }
    
    public boolean guardarEstudiante(Estudiante estudiante) throws MyException {
       LogInValidation nuevaValidacion = new LogInValidation(estudiante);
       try {
           nuevaValidacion.camposEstudiante();
           return true;
       } catch(MyException e) {
           throw e;
       }
    }
    
    public boolean registrarUsuario(Usuario nuevoUsuario) throws MyException {
        LogInValidation nuevaValidacion = new LogInValidation(nuevoUsuario);
        nuevaValidacion.camposUsuario();
        
        return repositorio.registrarUsuario(nuevoUsuario);
    }
}
