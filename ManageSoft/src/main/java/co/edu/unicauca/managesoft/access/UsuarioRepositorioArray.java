/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.Coordinador;
import co.edu.unicauca.managesoft.entities.Empresa;
import co.edu.unicauca.managesoft.entities.Estudiante;
import co.edu.unicauca.managesoft.entities.Usuario;
import co.edu.unicauca.managesoft.entities.enumTipoUsuario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jutak
 */
public class UsuarioRepositorioArray implements IUsuarioRepositorio {

    public static List<Usuario> usuariosArray;
    public static IEmpresaRepositorio repositorioEmpresa;
    public static ICoordinadorRepositorio repositorioCoordinador;
    public static IEstudianteRepositorio repositorioEstudiante;

    public UsuarioRepositorioArray() {
        usuariosArray = new ArrayList();
        usuariosArray.add(new Usuario("coordinador1", "pass123", enumTipoUsuario.COORDINADOR));
        usuariosArray.add(new Usuario("estudiante1", "pass456", enumTipoUsuario.ESTUDIANTE));
        usuariosArray.add(new Usuario("empresa1", "pass789", enumTipoUsuario.EMPRESA));
        usuariosArray.add(new Usuario("estudiante2", "pass101", enumTipoUsuario.ESTUDIANTE));
        usuariosArray.add(new Usuario("empresa2", "pass202", enumTipoUsuario.EMPRESA));

    }
    
    @Override
    public void setRepositorioEmpresa(IEmpresaRepositorio repositorioEmpresa) {
        this.repositorioEmpresa = repositorioEmpresa;
    }

    @Override
    public void setRepositorioCoordinador(ICoordinadorRepositorio repositorioCoordinador) {
       this.repositorioCoordinador = repositorioCoordinador;
    }
    
    @Override
    public void setRepositorioEstudiante(IEstudianteRepositorio repositorioEstudiante) {
       this.repositorioEstudiante = repositorioEstudiante;
    }

    @Override
    public void setRepositorioProyecto(IProyectoRepositorio repositorioProyecto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    

    @Override
    public boolean registrarUsuario(Usuario nuevoUsuario) {
        if (!existeUsuario(nuevoUsuario.getNombreUsuario())) {
            // Se agrega un nuevo usuario con la informacion del usuario ingresado
            usuariosArray.add(new Usuario(nuevoUsuario.getNombreUsuario(), nuevoUsuario.getContrasenaUsuario(), nuevoUsuario.getTipoUsuario()));
            
            // Se verifica que tipo de usuario es (Empresa o Estudiante) y se guarda en su respectivo repositorio
            if ((nuevoUsuario instanceof Empresa)) {
                return repositorioEmpresa.guardar((Empresa) nuevoUsuario);
            } else if ((nuevoUsuario instanceof Estudiante)) {
                return repositorioEstudiante.guardar((Estudiante) nuevoUsuario);
            }
        }
        return false;
    }

    // Al iniciar sesion se obtiene una instancia del usuario que ingreso
    @Override
    public Usuario iniciarSesion(String nombreUsuario, String contrasenaUsuario) {
        for (Usuario usuario : usuariosArray) {
            if (usuario.getNombreUsuario().equals(nombreUsuario) && usuario.getContrasenaUsuario().equals(contrasenaUsuario)) {
                // Determinar el tipo de usuario y buscarlo en su respectivo array
                switch (usuario.getTipoUsuario()) {
                    case EMPRESA:
                        return repositorioEmpresa.buscarEmpresa(nombreUsuario, contrasenaUsuario);
                    
                    // case COORDINADOR:
                        // return buscarEnArray(coordinadoresArray, nombreUsuario);
                        
                    case ESTUDIANTE:
                        return repositorioEstudiante.buscarEstudiante(nombreUsuario, contrasenaUsuario);
                    
                    default:
                        return null; // Tipo de usuario desconocido
                }
            }
        }
        return null;
    }

    /*@Override
    public List<Empresa> listarEmpresas() {
        return empresasArray;
    }*/
    private boolean existeUsuario(String nombreUsuario) {
        for (Usuario usuario : usuariosArray) {
            if (usuario.getNombreUsuario().equals(nombreUsuario)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setRepositorioCorreo(INotificacionRepositorio repositorioCorreo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
