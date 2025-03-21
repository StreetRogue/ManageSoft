/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.services;

import co.edu.unicauca.managesoft.access.INotificacionRepositorio;
import co.edu.unicauca.managesoft.entities.Coordinador;
import co.edu.unicauca.managesoft.entities.Correo;
import co.edu.unicauca.managesoft.entities.Estudiante;
import co.edu.unicauca.managesoft.entities.Proyecto;

/**
 *
 * @author juane
 */
public class NotificacionServices {

    private INotificacionRepositorio repositorio;

    public NotificacionServices(INotificacionRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public boolean guardarCorreo(Correo correo, Estudiante estudiante, Proyecto proyecto) {
        return repositorio.enviarCorreo(correo, estudiante, proyecto);
    }
    
    public boolean enviarComentario(String comentario, Coordinador coordinador, Proyecto proyecto){
        return repositorio.enviarComentario(comentario, coordinador, proyecto);
    }

}
