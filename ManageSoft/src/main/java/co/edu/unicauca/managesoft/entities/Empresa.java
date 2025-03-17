/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.entities;

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
    private String nombresContactoEmpresa;
    private String apellidoContactoEmpresa;
    private String cargoContactoEmpresa;
    private List<Proyecto> listaProyectos;

    public Empresa(String nitEmpresa, String nombreEmpresa, String emailEmpresa, String sectorEmpresa, String contactoEmpresa, String nombresContactoEmpresa, String apellidoContactoEmpresa, String cargoContactoEmpresa, String nombreUsuario, String contrasenaUsuario) {
        super(nombreUsuario, contrasenaUsuario, enumTipoUsuario.EMPRESA);
        this.nitEmpresa = nitEmpresa;
        this.nombreEmpresa = nombreEmpresa;
        this.emailEmpresa = emailEmpresa;
        this.sectorEmpresa = sectorEmpresa;
        this.contactoEmpresa = contactoEmpresa;
        this.nombresContactoEmpresa = nombresContactoEmpresa;
        this.apellidoContactoEmpresa = apellidoContactoEmpresa;
        this.cargoContactoEmpresa = cargoContactoEmpresa;
        this.listaProyectos = new ArrayList<>();
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

    public String getNombresContactoEmpresa() {
        return nombresContactoEmpresa;
    }

    public void setNombresContactoEmpresa(String nombresContactoEmpresa) {
        this.nombresContactoEmpresa = nombresContactoEmpresa;
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
    
    
}
