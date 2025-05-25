/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.Estudiante;
import java.util.List;

/**
 *
 * @author jutak
 */
public interface IEstudianteRepositorio {
    boolean guardar(Estudiante nuevoEstudiante);
    Estudiante buscarEstudiante(String nombreUsuario, String contrasenaUsuario);
    List<Estudiante> listarEstudiantes();
    int cantidadEstudiantes(); 
}
