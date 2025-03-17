/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.infra;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jutak
 */
public abstract class Subject {
    private List<IObserver> listaObservadores;

    public Subject() {
    }
    
    public void agregarObservador(IObserver nuevoObservador) {
        if (listaObservadores == null) {
            this.listaObservadores = new ArrayList<>();
        }
        listaObservadores.add(nuevoObservador);
    }
    
    public void notificarCambios(IObserver nuevoObservador) {
        for (IObserver each : listaObservadores) {
            each.actualizar();
        }
    }
    
    
}
