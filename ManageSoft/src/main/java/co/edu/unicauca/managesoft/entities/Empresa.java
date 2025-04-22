/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.entities;

import co.edu.unicauca.managesoft.access.IProyectoRepositorio;
import co.edu.unicauca.managesoft.services.ProyectoServices;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jutak
 */
public class Empresa extends Usuario {
    private String nitEmpresa;
    private String nombreEmpresa;
    private String emailEmpresa;
    private String sectorEmpresa;
    private String contactoEmpresa;
    private String nombreContactoEmpresa;
    private String apellidoContactoEmpresa;
    private String cargoContactoEmpresa;
    private IProyectoRepositorio repositorioProyectos;
    
    public Empresa(String nitEmpresa, String nombreEmpresa, String emailEmpresa, String sectorEmpresa, String contactoEmpresa, String nombreContactoEmpresa, String apellidoContactoEmpresa, String cargoContactoEmpresa) {
        this.nitEmpresa = nitEmpresa;
        this.nombreEmpresa = nombreEmpresa;
        this.emailEmpresa = emailEmpresa;
        this.sectorEmpresa = sectorEmpresa;
        this.contactoEmpresa = contactoEmpresa;
        this.nombreContactoEmpresa = nombreContactoEmpresa;
        this.apellidoContactoEmpresa = apellidoContactoEmpresa;
        this.cargoContactoEmpresa = cargoContactoEmpresa;
    }

    public Empresa(String nitEmpresa, String nombreEmpresa, String emailEmpresa, String sectorEmpresa, String contactoEmpresa, String nombreContactoEmpresa, String apellidoContactoEmpresa, String cargoContactoEmpresa, String nombreUsuario, String contrasenaUsuario) {
        super(nombreUsuario, contrasenaUsuario, enumTipoUsuario.EMPRESA);
        this.nitEmpresa = nitEmpresa;
        this.nombreEmpresa = nombreEmpresa;
        this.emailEmpresa = emailEmpresa;
        this.sectorEmpresa = sectorEmpresa;
        this.contactoEmpresa = contactoEmpresa;
        this.nombreContactoEmpresa = nombreContactoEmpresa;
        this.apellidoContactoEmpresa = apellidoContactoEmpresa;
        this.cargoContactoEmpresa = cargoContactoEmpresa;
    }
    
    public String getNitEmpresa() {
        return nitEmpresa;
    }

    public void setNitEmpresa(String nitEmpresa) {
        this.nitEmpresa = nitEmpresa;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getEmailEmpresa() {
        return emailEmpresa;
    }

    public void setEmailEmpresa(String emailEmpresa) {
        this.emailEmpresa = emailEmpresa;
    }

    public String getSectorEmpresa() {
        return sectorEmpresa;
    }

    public void setSectorEmpresa(String sectorEmpresa) {
        this.sectorEmpresa = sectorEmpresa;
    }

    public String getContactoEmpresa() {
        return contactoEmpresa;
    }

    public void setContactoEmpresa(String contactoEmpresa) {
        this.contactoEmpresa = contactoEmpresa;
    }

    public String getNombreContactoEmpresa() {
        return nombreContactoEmpresa;
    }

    public void setNombreContactoEmpresa(String nombreContactoEmpresa) {
        this.nombreContactoEmpresa = nombreContactoEmpresa;
    }

    public String getApellidoContactoEmpresa() {
        return apellidoContactoEmpresa;
    }

    public void setApellidoContactoEmpresa(String apellidoContactoEmpresa) {
        this.apellidoContactoEmpresa = apellidoContactoEmpresa;
    }

    public String getCargoContactoEmpresa() {
        return cargoContactoEmpresa;
    }

    public void setCargoContactoEmpresa(String cargoContactoEmpresa) {
        this.cargoContactoEmpresa = cargoContactoEmpresa;
    }

    public IProyectoRepositorio getRepositorioProyectos() {
        return repositorioProyectos;
    }

    public void setRepositorioProyectos(IProyectoRepositorio repositorioProyectos) {
        this.repositorioProyectos = repositorioProyectos;
    }
    
    public boolean agregarProyecto(IProyectoRepositorio repositorioProyecto, String nombreProyecto, String resumenProyecto, String objetivoProyecto, String descripcionProyecto, String maximoMesesProyecto, String presupuestoProyecto) {
        ProyectoServices proyectoServices = new ProyectoServices(repositorioProyecto);
        Proyecto proyecto;
        
        if (presupuestoProyecto != null) proyecto = new Proyecto(nombreProyecto, resumenProyecto, objetivoProyecto, descripcionProyecto, maximoMesesProyecto, presupuestoProyecto);
        else proyecto = new Proyecto(nombreProyecto, resumenProyecto, objetivoProyecto, descripcionProyecto, maximoMesesProyecto);
        
        boolean guardar = proyectoServices.guardarProyecto(proyecto, this);
        
        return guardar;
    }
    
    public List<Proyecto> listarProyectos() {
        return repositorioProyectos.listarProyectos(this.nitEmpresa);
    }


}
