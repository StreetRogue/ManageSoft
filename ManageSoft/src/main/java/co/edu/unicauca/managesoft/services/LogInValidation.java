/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.services;

import co.edu.unicauca.managesoft.entities.Empresa;
import co.edu.unicauca.managesoft.entities.Estudiante;
import co.edu.unicauca.managesoft.entities.Usuario;
import co.edu.unicauca.managesoft.infra.MyException;

/**
 *
 * @author jutak
 */
public class LogInValidation {

    private Usuario usuario;

    public LogInValidation(Usuario nuevoUsuario) {
        this.usuario = nuevoUsuario;
    }

    public void camposUsuario() throws MyException {
        if (usuario.getNombreUsuario().isBlank()|| usuario.getContrasenaUsuario().isBlank() || usuario.getTipoUsuario().equals(null)) {
            throw new MyException("Todos los campos deben rellenarse");
        }
    }
    
    public void validarUsuario() throws MyException {
        if(usuario == null){
            throw new MyException("Usuario y/o Contraseña Invalidos");
        }
    }
    
    public void camposEmpresa() throws MyException {
        Empresa empresaRegistrada = ((Empresa) usuario);
        
        if (empresaRegistrada.getNitEmpresa().isBlank()) throw new MyException("Debe ingresar el NIT de la empresa");
        if (empresaRegistrada.getNombreEmpresa().isBlank()) throw new MyException("Dene ingresar el nombre de la empresa");
        if (empresaRegistrada.getEmailEmpresa().isBlank()) throw new MyException("Debe ingresar el email de la empresa");
        if (empresaRegistrada.getSectorEmpresa().isBlank()) throw new MyException("Debe ingresar el sector de la empresa");
        if (empresaRegistrada.getContactoEmpresa().isBlank()) throw new MyException("Debe ingresar el telefono del contacto de la empresa");
        if (empresaRegistrada.getNombreContactoEmpresa().isBlank()) throw new MyException("Debe ingresar el nombre del contacto de la empresa");
        if (empresaRegistrada.getApellidoContactoEmpresa().isBlank()) throw new MyException("Debe ingresar el apellido del contacto de la empresa");
        if (empresaRegistrada.getCargoContactoEmpresa().isBlank()) throw new MyException("Debe ingresar el cargo del contacto de la empresa");
    }
    
    public void camposEstudiante() throws MyException {
        Estudiante estudianteRegistrado = ((Estudiante) usuario);
        
        if (estudianteRegistrado.getNombreEstudiante().isBlank()) throw new MyException("Debe ingresar el nombre del estudiante");
        if (estudianteRegistrado.getApellidoEstudiante().isBlank()) throw new MyException("Debe ingresar el apellido del estudiante");
        if (estudianteRegistrado.getCodigoSimcaEstudiante().isBlank()) throw new MyException("Debe ingresar el codigo de simca del estudiante");
        if (estudianteRegistrado.getEmailEstudiante().isBlank()) throw new MyException("Debe ingresar el email del estudiante");
    }
}
