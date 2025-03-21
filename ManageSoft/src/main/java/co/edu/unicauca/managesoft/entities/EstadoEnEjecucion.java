package co.edu.unicauca.managesoft.entities;

public class EstadoEnEjecucion implements IEstadoProyecto {

    @Override
    public void cambiarEstado(Proyecto proyecto, IEstadoProyecto nuevoEstado) {
        if (!proyecto.getEstadoProyecto().equals(nuevoEstado)) {
            proyecto.setEstadoProyecto(nuevoEstado);
        }
    }

    @Override
    public String obtenerEstado() {
        return "EN_EJECUCION";
    }

    @Override
    public String toString() {
        return obtenerEstado(); 
    }
}
