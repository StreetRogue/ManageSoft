package co.edu.unicauca.microcoordinador.entities.state;

import co.edu.unicauca.microcoordinador.entities.EnumEstadoProyecto;

public class EstadoProyectoFactory {
    public static EstadoProyecto obtenerEstado(EnumEstadoProyecto estado) {
        //Aunque hay un switch aqui, no viola el principio OCP porque está contenido en un único punto de cambio: la fábrica.
        return switch (estado) {
            case RECIBIDO -> new EstadoRecibido();
            case ACEPTADO -> new EstadoAceptado();
            case RECHAZADO -> new EstadoRechazado();
            case EJECUCION -> new EstadoEjecucion();
            case CERRADO -> new EstadoCerrado();
        };
    }
}
