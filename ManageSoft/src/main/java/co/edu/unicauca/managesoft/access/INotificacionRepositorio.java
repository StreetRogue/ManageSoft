/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.Coordinador;
import co.edu.unicauca.managesoft.entities.Correo;
import co.edu.unicauca.managesoft.entities.Estudiante;
import co.edu.unicauca.managesoft.entities.Proyecto;

/**
 *
 * @author juane
 */
public interface INotificacionRepositorio {
        boolean enviarCorreo(Correo correo, Estudiante estudiante, Proyecto proyecto);
        boolean enviarComentario(String comentario, Coordinador coordinador ,Proyecto Proyecto);
}
