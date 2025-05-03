/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.infra;

import co.edu.unicauca.managesoft.entities.Empresa;
import co.edu.unicauca.managesoft.entities.IEstadoProyecto;
import co.edu.unicauca.managesoft.entities.Proyecto;
import co.edu.unicauca.managesoft.infra.Subject;

/**
 *
 * @author camac
 */
public class ProyectTable extends Subject {

    private int idProyecto;
    private String nombreProyecto;
    private String resumenProyecto;
    private String objetivoProyecto;
    private String descripcionProyecto;
    private String maximoMesesProyecto;
    private String presupuestoProyecto;
    private String fechaPublicacionProyecto;
    private IEstadoProyecto estadoProyecto;
    private String nombreEmpresa;
    private Empresa empresa;
    private boolean correoEnviado;

    public ProyectTable() {
    }

    public ProyectTable(int idProyecto, String nombreProyecto, String resumenProyecto, String objetivoProyecto, String descripcionProyecto, String maximoMesesProyecto, String presupuestoProyecto, String fechaPublicacionProyecto, IEstadoProyecto estadoProyecto, Empresa empresa, boolean correoEnviado) {
        this.idProyecto = idProyecto;
        this.nombreProyecto = nombreProyecto;
        this.resumenProyecto = resumenProyecto;
        this.objetivoProyecto = objetivoProyecto;
        this.descripcionProyecto = descripcionProyecto;
        this.maximoMesesProyecto = maximoMesesProyecto;
        this.presupuestoProyecto = presupuestoProyecto;
        this.fechaPublicacionProyecto = fechaPublicacionProyecto;
        this.estadoProyecto = estadoProyecto;
        this.empresa = empresa;
        this.correoEnviado = correoEnviado;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getResumenProyecto() {
        return resumenProyecto;
    }

    public void setResumenProyecto(String resumenProyecto) {
        this.resumenProyecto = resumenProyecto;
    }

    public String getObjetivoProyecto() {
        return objetivoProyecto;
    }

    public void setObjetivoProyecto(String objetivoProyecto) {
        this.objetivoProyecto = objetivoProyecto;
    }

    public String getDescripcionProyecto() {
        return descripcionProyecto;
    }

    public void setDescripcionProyecto(String descripcionProyecto) {
        this.descripcionProyecto = descripcionProyecto;
    }

    public String getMaximoMesesProyecto() {
        return maximoMesesProyecto;
    }

    public void setMaximoMesesProyecto(String maximoMesesProyecto) {
        this.maximoMesesProyecto = maximoMesesProyecto;
    }

    public String getPresupuestoProyecto() {
        return presupuestoProyecto;
    }

    public void setPresupuestoProyecto(String presupuestoProyecto) {
        this.presupuestoProyecto = presupuestoProyecto;
    }

    public String getFechaPublicacionProyecto() {
        return fechaPublicacionProyecto;
    }

    public void setFechaPublicacionProyecto(String fechaPublicacionProyecto) {
        this.fechaPublicacionProyecto = fechaPublicacionProyecto;
    }

    public IEstadoProyecto getEstadoProyecto() {
        return estadoProyecto;
    }

    public void setEstadoProyecto(IEstadoProyecto estadoProyecto) {
        this.estadoProyecto = estadoProyecto;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    

    public boolean isCorreoEnviado() {
        return correoEnviado;
    }

    public void setCorreoEnviado(boolean correoEnviado) {
        this.correoEnviado = correoEnviado;
    }

    public Proyecto toProyecto() {
        Proyecto proyecto = new Proyecto();

        proyecto.setIdProyecto(this.idProyecto);
        proyecto.setNombreProyecto(this.nombreProyecto);
        proyecto.setResumenProyecto(this.resumenProyecto);
        proyecto.setObjetivoProyecto(this.objetivoProyecto);
        proyecto.setDescripcionProyecto(this.descripcionProyecto);
        proyecto.setMaximoMesesProyecto(this.maximoMesesProyecto);
        proyecto.setPresupuestoProyecto(this.presupuestoProyecto);
        proyecto.setFechaPublicacionProyecto(this.fechaPublicacionProyecto);
        proyecto.setEstadoProyecto(this.estadoProyecto);
        proyecto.setEmpresa(this.empresa);

        return proyecto;
    }

}
