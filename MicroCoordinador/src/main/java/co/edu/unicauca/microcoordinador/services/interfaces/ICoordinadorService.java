package co.edu.unicauca.microcoordinador.services.interfaces;

import co.edu.unicauca.microcoordinador.entities.Coordinador;
import co.edu.unicauca.microcoordinador.infra.dto.CambioEstadoDto;
import co.edu.unicauca.microcoordinador.infra.dto.DetalleProyectoDto;
import co.edu.unicauca.microcoordinador.infra.dto.ProyectoResumenDto;

import java.util.List;

public interface ICoordinadorService {
    List<ProyectoResumenDto> obtenerProyectosDelCoordinador(Long idCoordinador);
    DetalleProyectoDto obtenerDetalleProyecto(Long idProyecto);
    void cambiarEstadoProyecto(Long idProyecto, CambioEstadoDto cambioEstadoDto);
    Coordinador guardarCoordinador(Coordinador coordinador); // <-- ¡Este es el nuevo!
}

