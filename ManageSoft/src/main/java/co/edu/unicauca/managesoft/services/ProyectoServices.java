/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.services;

import co.edu.unicauca.managesoft.access.IProyectoRepositorio;
import co.edu.unicauca.managesoft.entities.Empresa;
import co.edu.unicauca.managesoft.entities.Proyecto;
import co.edu.unicauca.managesoft.infra.ProyectTable;
import java.util.List;

/**
 *
 * @author camac
 */
public class ProyectoServices {
    private IProyectoRepositorio repositorio;
    
    public ProyectoServices(IProyectoRepositorio repositorio){
        this.repositorio = repositorio;
    }
    
    public boolean guardarProyecto(Proyecto nuevoProyecto, Empresa empresa){
        return repositorio.guardarProyecto(nuevoProyecto, empresa);
    }
    
    public List<ProyectTable> listarProyectosGeneral(){
        return repositorio.listarProyectosGeneral();
    }
    
    public List<ProyectTable> listarProyectosGeneralEstudiantes(){
        return repositorio.listarProyectosGeneralEstudiantes();
    }
    
    public Proyecto encontrarPorId(String idProyectoStr){
        return repositorio.encontrarPorId(idProyectoStr);
    }
    
    public boolean actualizarEstadoProyecto(Proyecto proyecto, String nuevoEstado){
        return repositorio.actualizarEstadoProyecto(proyecto, nuevoEstado);
    }
    
    public int cantProyectosEvaluados(String periodoAcademico){
        return repositorio.cantProyectosEvaluados(periodoAcademico);
    }
    
    public int cantProyectoporEstado(String estado, String periodoAcademico){
        return repositorio.cantProyectoporEstado(estado, periodoAcademico);
    }
    
    public int cantTasaAceptacion(String periodoAcademico){
        return repositorio.cantTasaAceptacion(periodoAcademico);
    }

    public int avgTiempoAceptacion(){
        return repositorio.avgProyectoDiasEnAceptar();
    }

}
