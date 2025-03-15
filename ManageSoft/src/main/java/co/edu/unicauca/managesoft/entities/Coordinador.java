/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.entities;

import java.util.List;

/**
 *
 * @author jutak
 */
public class Coordinador extends Usuario{
    public Coordinador(String nombreUsuario, String contrasenaUsuario) {
        super(nombreUsuario, contrasenaUsuario, enumTipoUsuario.COORDINADOR);
    }
}
