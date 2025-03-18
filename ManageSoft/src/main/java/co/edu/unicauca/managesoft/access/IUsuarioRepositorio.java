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
    void setRepositorioEmpresa(IEmpresaRepositorio repositorioEmpresa);
    void setRepositorioCoordinador(ICoordinadorRepositorio repositorioCoordinador);
    void setRepositorioEstudiante(IEstudianteRepositorio repositorioEstudiante);
    boolean registrarUsuario(Usuario nuevoUsuario);
    Usuario iniciarSesion(String nombreUsuario, String contrasenaUsuario);
    // List<Usuario> listarUsuarios();
}
