/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.Usuario;
import java.util.List;

/**
 *
 * @author jutak
 */
public interface IUsuarioRepositorio {
    boolean guardar(Usuario nuevoUsuario);
    boolean autenticarInicioSesion(String nombreUsuario, String contrasenaUsuario);
    // List<Usuario> listarUsuarios();
}
