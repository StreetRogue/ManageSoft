/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.entities;

/**
 *
 * @author jutak
 */
public class EstadoRechazado implements IEstadoProyecto {
    @Override
    public void cambiarEstado(Proyecto proyecto, IEstadoProyecto nuevoEstado) {
        proyecto.setEstadoProyecto(nuevoEstado);
    }

    @Override
    public String obtenerEstado() {
        return "RECHAZADO";
    }
}
