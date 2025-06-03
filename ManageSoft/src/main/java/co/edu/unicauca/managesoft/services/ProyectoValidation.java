/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.services;

import co.edu.unicauca.managesoft.entities.Empresa;
import co.edu.unicauca.managesoft.entities.Proyecto;
import co.edu.unicauca.managesoft.infra.MyException;

/**
 *
 * @author camac
 */
public class ProyectoValidation {
    private Proyecto proyecto;

    public ProyectoValidation(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
    
    public void validarCamposProyecto(){
         if (proyecto.getNombreProyecto().isBlank()) throw new MyException("Debe ingresar el nombre del proyecto");
         if (proyecto.getDescripcionProyecto().isBlank()) throw new MyException("Debe ingresar la descripcion del proyecto");
         if (proyecto.getObjetivoProyecto().isBlank()) throw new MyException("Debe ingresar el objetivo del proyecto");
         if (proyecto.getMaximoMesesProyecto().isBlank()) throw new MyException("Debe ingresar el tiempo estipulado del proyecto");
         if (proyecto.getResumenProyecto().isBlank()) throw new MyException("Debe ingresar el resumen del proyecto");
    }
}
