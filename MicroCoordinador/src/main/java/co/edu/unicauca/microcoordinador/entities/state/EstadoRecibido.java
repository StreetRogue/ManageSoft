package co.edu.unicauca.microcoordinador.entities.state;

import co.edu.unicauca.microcoordinador.entities.EnumEstadoProyecto;

public class EstadoRecibido implements EstadoProyecto {
    @Override
    public EnumEstadoProyecto getNombreEstado() {
        return EnumEstadoProyecto.RECIBIDO;
    }
}



