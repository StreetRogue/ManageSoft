/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.entities;

import co.edu.unicauca.managesoft.infra.Subject;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author jutak
 */
public class Proyecto extends Subject {

    private int idProyecto;
    private String nombreProyecto;
    private String resumenProyecto;
    private String objetivoProyecto;
    private String descripcionProyecto;
    private String maximoMesesProyecto;
    private String presupuestoProyecto;
    private String fechaPublicacionProyecto;
    private IEstadoProyecto estadoProyecto;
<<<<<<< Updated upstream
=======
    private List<Estudiante> estudiantesPostulados;
    private List<Estudiante> estudiantesAceptados;
    private Empresa empresa;
>>>>>>> Stashed changes

    public Proyecto() {
    }

    // Constructor CON el presupuesto
    public Proyecto(String nombreProyecto, String resumenProyecto, String objetivoProyecto, String descripcionProyecto, String maximoMesesProyecto, String presupuestoProyecto, Empresa empresa) {
        this.nombreProyecto = nombreProyecto;
        this.resumenProyecto = resumenProyecto;
        this.objetivoProyecto = objetivoProyecto;
        this.descripcionProyecto = descripcionProyecto;
        this.maximoMesesProyecto = maximoMesesProyecto;
        this.presupuestoProyecto = presupuestoProyecto;

        // Obtener fecha actual del computador y formatearla
        Date fechaActual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        this.fechaPublicacionProyecto = formato.format(fechaActual);

        this.estadoProyecto = new EstadoRecibido();

        this.empresa = empresa;
    }

    // Constructor SIN el presupuesto
    public Proyecto(String nombreProyecto, String resumenProyecto, String objetivoProyecto, String descripcionProyecto, String maximoMesesProyecto, Empresa empresa) {
        this.nombreProyecto = nombreProyecto;
        this.resumenProyecto = resumenProyecto;
        this.objetivoProyecto = objetivoProyecto;
        this.descripcionProyecto = descripcionProyecto;
        this.maximoMesesProyecto = maximoMesesProyecto;

        // Obtener fecha actual del computador y formatearla
        Date fechaActual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        this.fechaPublicacionProyecto = formato.format(fechaActual);

        this.estadoProyecto = new EstadoRecibido();

        this.empresa = empresa;
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
        if (this.estadoProyecto != estadoProyecto) { // ✅ Evita recursión infinita
            this.estadoProyecto = estadoProyecto;  // Asigna el nuevo estado
            setChanged();
            notificarCambios();

            // Solo llama a cambiarEstado si es necesario
            if (estadoProyecto != null) {
                estadoProyecto.cambiarEstado(this, estadoProyecto);
            }
        }
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public void agregarEstudiante(Estudiante estudiante){
        estudiantesPostulados.add(estudiante);
    }
}
