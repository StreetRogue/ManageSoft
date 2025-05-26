/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.infra;

import co.edu.unicauca.managesoft.entities.Proyecto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author camac
 */
public class CareTakerProyecto {

    private List<Proyecto.Memento> historial = new ArrayList<>();

    public void addMemento(Proyecto.Memento m) {
        historial.add(m);
    }

    public List<Proyecto.Memento> getHistorial() {
        return historial;
    }
}
