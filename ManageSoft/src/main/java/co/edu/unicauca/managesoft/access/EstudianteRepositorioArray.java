/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.Estudiante;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jutak
 */
public class EstudianteRepositorioArray implements IEstudianteRepositorio {

    public static List<Estudiante> estudiantesArray;

    public EstudianteRepositorioArray() {
        estudiantesArray = new ArrayList<>();
    }

    @Override
    public boolean guardar(Estudiante nuevoEstudiante) {
        if (!existeSimca(nuevoEstudiante.getCodigoSimcaEstudiante())){
            estudiantesArray.add(nuevoEstudiante);
            return true;
        }
        return false;
    }
    
    @Override
    public Estudiante buscarEstudiante(String nombreUsuario) {
        for (Estudiante estudiante : estudiantesArray) {
            if (estudiante.getNombreUsuario().equals(nombreUsuario)) {
                return estudiante;
            }
        }
        
        return null;
    }
    
    @Override
    public Estudiante buscarEstudiante(String nombreUsuario, String contrasenaUsuario) {
        for (Estudiante estudiante : estudiantesArray) {
            if (estudiante.getNombreUsuario().equals(nombreUsuario) && estudiante.getContrasenaUsuario().equals(contrasenaUsuario)) {
                return estudiante;
            }
        }
        
        return null;
    }

    @Override
    public List<Estudiante> listarEstudiantes() {
        return estudiantesArray;
    }
    
    private boolean existeSimca(String codigo){
        for (Estudiante estudiante: estudiantesArray){
            if (estudiante.getCodigoSimcaEstudiante().equals(codigo)){
                return true;
            }
        }
        return false;
    }
    
}
