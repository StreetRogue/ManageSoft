package co.edu.unicauca.companyServices.entities;


public enum EstadoProyecto {
    RECIBIDO,
    ACEPTADO,
    RECHAZADO,
    EN_EJECUCION,
    CERRADO;

    public boolean puedeCambiarA(EstadoProyecto nuevoEstado) {
        return switch (this) {
            case RECIBIDO -> nuevoEstado == ACEPTADO || nuevoEstado == RECHAZADO;
            case ACEPTADO -> nuevoEstado == EN_EJECUCION;
            case EN_EJECUCION -> nuevoEstado == CERRADO;
            default -> false;
        };
    }
}
