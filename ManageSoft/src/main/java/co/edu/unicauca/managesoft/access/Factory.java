/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.access;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jutak
 */
public class Factory {

    private static Factory instancia;

    private Map<String, IEmpresaRepositorio> repositoriosEmpresa;
    private Map<String, ICoordinadorRepositorio> repositoriosCoordinador;
    private Map<String, IEstudianteRepositorio> repositoriosEstudiante;
    private Map<String, IUsuarioRepositorio> repositoriosUsuario;
    private Map<String, IProyectoRepositorio> repositoriosProyecto;
    private Map<String, INotificacionRepositorio> repositorioCorreo;

    private Factory() {
        repositoriosEmpresa = new HashMap<>();
        repositoriosCoordinador = new HashMap<>();
        repositoriosEstudiante = new HashMap<>();
        repositoriosUsuario = new HashMap<>();
        repositoriosProyecto = new HashMap<>();
        repositorioCorreo = new HashMap<>();

        // Repositorios Empresa
        repositoriosEmpresa.put("ARRAYS", new EmpresaRepositorioArray());
        repositoriosEmpresa.put("NEONDB", new EmpresaRepositorioNeonDB());

        // Repositorios Coordinador
        repositoriosCoordinador.put("NEONDB", new CoordinadorRepositorioNeonDB());

        // Repositorios Estudiante
        repositoriosEstudiante.put("ARRAYS", new EstudianteRepositorioArray());
        repositoriosEstudiante.put("NEONDB", new EstudianteRepositorioNeonDB());

        // Repositorios Usuarios
        repositoriosUsuario.put("ARRAYS", new UsuarioRepositorioArray());
        repositoriosUsuario.put("NEONDB", new UsuarioRepositorioNeonDB());

        //Repositorios Proyectos
        repositoriosProyecto.put("NEONDB", new ProyectoRepositorioNeonDB());
        
        repositorioCorreo.put("NEONDB", new NotificacionRepositorioNeonDB());
    }

    /**
     * Clase singleton
     *
     * @return
     */
    public static Factory getInstancia() {

        if (instancia == null) {
            instancia = new Factory();
        }
        return instancia;

    }

    /**
     * Método que crea una instancia concreta de la jerarquia ICompanyRepository
     *
     * @param repository cadena que indica qué tipo de clase hija debe
     * instanciar
     * @return una clase hija de la abstracción IProductRepository
     */
    public IEmpresaRepositorio getRepositorioEmpresa(String repositorio) {

        IEmpresaRepositorio resultado = null;

        if (repositoriosEmpresa.containsKey(repositorio)) {
            resultado = repositoriosEmpresa.get(repositorio);
        }

        return resultado;

    }

    public ICoordinadorRepositorio getRepositorioCoordinador(String repositorio) {

        ICoordinadorRepositorio resultado = null;

        if (repositoriosCoordinador.containsKey(repositorio)) {
            resultado = repositoriosCoordinador.get(repositorio);
        }

        return resultado;

    }

    public IEstudianteRepositorio getRepositorioEstudiante(String repositorio) {

        IEstudianteRepositorio resultado = null;

        if (repositoriosEstudiante.containsKey(repositorio)) {
            resultado = repositoriosEstudiante.get(repositorio);
        }

        return resultado;

    }

    public IUsuarioRepositorio getRepositorioUsuario(String repositorio) {

        IUsuarioRepositorio resultado = null;

        if (repositoriosUsuario.containsKey(repositorio)) {
            resultado = repositoriosUsuario.get(repositorio);
        }

        return resultado;
    }

    public IProyectoRepositorio getRepositorioProyecto(String repositorio) {

        IProyectoRepositorio resultado = null;

        if (repositoriosProyecto.containsKey(repositorio)) {
            resultado = repositoriosProyecto.get(repositorio);
        }

        return resultado;
    }
    
    public INotificacionRepositorio getNotificacionRepositorio(String repositorio) {

        INotificacionRepositorio resultado = null;

        if (repositorioCorreo.containsKey(repositorio)) {
            resultado = repositorioCorreo.get(repositorio);
        }

        return resultado;
    }
}
