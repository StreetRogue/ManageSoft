/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.Empresa;
import co.edu.unicauca.managesoft.entities.IEstadoProyecto;
import co.edu.unicauca.managesoft.infra.ProyectTable;
import co.edu.unicauca.managesoft.entities.Proyecto;
import java.util.List;

/**
 *
 * @author camac
 */
public interface IProyectoRepositorio {
    boolean guardarProyecto(Proyecto nuevoProyecto, Empresa empresa);
    List<Proyecto> listarProyectos(String nitEmpresa);
    List<ProyectTable> listarProyectosGeneral();
    List<ProyectTable> listarProyectosGeneralEstudiantes();
    IEstadoProyecto obtenerEstadoProyecto(String estado);
    Proyecto encontrarPorId(String proyectos);
    boolean actualizarEstadoProyecto(Proyecto idProyecto, String nuevoEstado);
    int cantProyectoporEstado(String estado, String periodoAcedemico);
    int cantProyectosEvaluados(String periodoAcademico);
    int cantTasaAceptacion(String periodoAcademico);
    int avgProyectoDiasEnAceptar();

}
