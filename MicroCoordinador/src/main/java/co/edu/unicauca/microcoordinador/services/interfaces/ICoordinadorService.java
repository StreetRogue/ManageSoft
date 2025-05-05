package co.edu.unicauca.microcoordinador.services.interfaces;

import co.edu.unicauca.microcoordinador.entities.Coordinador;
import co.edu.unicauca.microcoordinador.infra.dto.CambioEstadoDto;
import co.edu.unicauca.microcoordinador.infra.dto.CoordinadorDto;
import co.edu.unicauca.microcoordinador.infra.dto.DetalleProyectoDto;
import co.edu.unicauca.microcoordinador.infra.dto.ProyectoResumenDto;

import java.util.List;

public interface ICoordinadorService {
    // Obtener proyectos de un coordinador
    List<ProyectoResumenDto> obtenerProyectosDelCoordinador(Long idCoordinador);

    // Obtener el detalle de un proyecto
    DetalleProyectoDto obtenerDetalleProyecto(Long idProyecto);

    // Cambiar el estado de un proyecto
    void cambiarEstadoProyecto(Long idProyecto, CambioEstadoDto cambioEstadoDto);

    // Guardar o actualizar coordinador desde la cola
    Coordinador guardarCoordinadorDesdeCola(CoordinadorDto dto);

    // Buscar un coordinador por nombre de usuario y contraseña
    Coordinador buscarCoordinador(String nombreUsuario, String contrasenaUsuario);
}

