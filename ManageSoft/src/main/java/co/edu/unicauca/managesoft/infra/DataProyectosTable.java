/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.infra;

/**
 *
 * @author jutak
 */
public class DataProyectosTable {
    private int idProyecto;
    private String nombreProyecto;
    private String resumenProyecto;
    private String maximoMesesProyecto;
    private String presupuestoProyecto;
    private String nombreEmpresa;

    public DataProyectosTable(int idProyecto, String nombreProyecto, String resumenProyecto, String maximoMesesProyecto, String presupuestoProyecto, String nombreEmpresa) {
        this.idProyecto = idProyecto;
        this.nombreProyecto = nombreProyecto;
        this.resumenProyecto = resumenProyecto;
        this.maximoMesesProyecto = maximoMesesProyecto;
        this.presupuestoProyecto = presupuestoProyecto;
        this.nombreEmpresa = nombreEmpresa;
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

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }
    
    
}
