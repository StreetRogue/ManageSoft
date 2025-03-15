/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.edu.unicauca.managesoft.entities;

/**
 *
 * @author jutak
 */
public interface IEstadoProyecto {
    void cambiarEstado(Proyecto proyecto, IEstadoProyecto nuevoEstado);
    String obtenerEstado();
}
