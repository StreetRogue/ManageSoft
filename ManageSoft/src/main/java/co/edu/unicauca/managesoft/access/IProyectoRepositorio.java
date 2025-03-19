/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.Empresa;
import co.edu.unicauca.managesoft.entities.Proyecto;
import java.util.List;

/**
 *
 * @author camac
 */
public interface IProyectoRepositorio {
    boolean guardarProyecto(Proyecto nuevoProyecto, Empresa empresa);
    List<Proyecto> listarProyectos(Empresa empresa);
}
