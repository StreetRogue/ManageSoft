/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.Coordinador;

/**
 *
 * @author camac
 */
public interface ICoordinadorRepositorio {

    Coordinador buscarCoordinador(String nombreUsuario);
    Coordinador buscarCoordinador(String nombreUsuario, String contrasenaUsuario);
}
