package co.edu.unicauca.managesoft.infra;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {

    private List<IObserver> listaObservadores;
    private boolean changed = false;

    public Subject() {
        listaObservadores = new ArrayList<>();
    }

    // Marca que el objeto ha cambiado
    protected void setChanged() {
        changed = true;
    }

    // Notifica a todos los observadores si el objeto ha cambiado
    public void notificarCambios() {
        if (changed) {
            for (IObserver observer : listaObservadores) {
                observer.actualizar();
            }
            changed = false;  // Resetea el estado de cambio
        }
    }

    // Agrega un nuevo observador a la lista
    public void agregarObservador(IObserver nuevoObservador) {
        listaObservadores.add(nuevoObservador);
    }
}
