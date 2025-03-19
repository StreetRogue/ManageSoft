/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.services;

import co.edu.unicauca.managesoft.access.IProyectoRepositorio;
import co.edu.unicauca.managesoft.entities.Proyecto;

/**
 *
 * @author camac
 */
public class ProyectoServices {
    private IProyectoRepositorio repositorio;
    
    public ProyectoServices(IProyectoRepositorio repositorio){
        this.repositorio = repositorio;
    }
    
    public boolean guardarProyecto(Proyecto nuevoProyecto){
        return repositorio.guardarProyecto(nuevoProyecto);
    }
}
