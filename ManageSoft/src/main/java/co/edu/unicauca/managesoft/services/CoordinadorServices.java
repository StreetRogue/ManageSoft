/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.services;

import co.edu.unicauca.managesoft.entities.IEstadoProyecto;
import co.edu.unicauca.managesoft.entities.Proyecto;
import java.util.List;

/**
 *
 * @author jutak
 */
public class CoordinadorServices {
    private List<Proyecto> listaProyectos;
    
    public void listarProyectos() {
        
    }

    /*public void verDetalles(Proyecto proyecto) {
        System.out.println("Detalles del proyecto:");
        System.out.println("Nombre: " + proyecto.getNombre());
        System.out.println("Descripción: " + proyecto.getDescripcion());
        System.out.println("Estado: " + proyecto.getEstadoActual());
        System.out.println("Empresa: " + proyecto.getEmpresa().getNombre());
        System.out.println("Email Empresa: " + proyecto.getEmpresa().getEmail());
    } */

    public void cambiarEstadoProyecto(Proyecto proyecto, IEstadoProyecto nuevoEstado, String comentario) {
        proyecto.setEstadoProyecto(nuevoEstado);
        // enviarCorreo(proyecto.getEmpresa(), proyecto, comentario);
    }

    /*private void enviarCorreo(Empresa empresa, Proyecto proyecto, String comentario) {
        System.out.println("Enviando correo a " + empresa.getEmail());
        System.out.println("Asunto: Cambio de estado del proyecto");
        System.out.println("Cuerpo: El estado del proyecto '" + proyecto.getNombre() + "' ha cambiado a: " + proyecto.getEstadoActual());
        System.out.println("Comentario: " + comentario);
    } */
}
