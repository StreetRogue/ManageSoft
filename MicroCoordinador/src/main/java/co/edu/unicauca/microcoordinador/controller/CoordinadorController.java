package co.edu.unicauca.microcoordinador.controller;

import co.edu.unicauca.microcoordinador.entities.Coordinador;
import co.edu.unicauca.microcoordinador.infra.dto.CambioEstadoDto;
import co.edu.unicauca.microcoordinador.infra.dto.DetalleProyectoDto;
import co.edu.unicauca.microcoordinador.infra.dto.ProyectoResumenDto;
import co.edu.unicauca.microcoordinador.services.interfaces.ICoordinadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/coordinador")
@RequiredArgsConstructor
public class CoordinadorController {

    private final ICoordinadorService coordinadorService;
    /**
     * Endpoint para listar todos los proyectos asociados a un coordinador.
     * Método: GET
     * URL: /api/coordinador/{idCoordinador}/proyectos
     *
     * @param idCoordinador ID del coordinador.
     * @return Lista resumida de proyectos.
     */
    // Obtener todos los proyectos asociados a un coordinador
    @GetMapping("/{idCoordinador}/proyectos")
    public ResponseEntity<List<ProyectoResumenDto>> listarProyectos(@PathVariable Long idCoordinador) {
        List<ProyectoResumenDto> proyectos = coordinadorService.obtenerProyectosDelCoordinador(idCoordinador);
        return ResponseEntity.ok(proyectos);
    }


    /**
     * Endpoint para obtener el detalle completo de un proyecto.
     * Método: GET
     * URL: /api/coordinador/proyecto/{idProyecto}
     *
     * @param idProyecto ID del proyecto.
     * @return Detalle completo del proyecto.
     */
    // Obtener el detalle de un proyecto
    @GetMapping("/proyecto/{idProyecto}")
    public ResponseEntity<DetalleProyectoDto> detalleProyecto(@PathVariable Long idProyecto) {
        DetalleProyectoDto detalle = coordinadorService.obtenerDetalleProyecto(idProyecto);
        return ResponseEntity.ok(detalle);
    }

    /**
     * Endpoint para cambiar el estado de un proyecto.
     * Método: PUT
     * URL: /api/coordinador/proyecto/{idProyecto}/estado
     *
     * @param idProyecto ID del proyecto.
     * @param cambioEstadoDto Objeto que contiene el nuevo estado y comentario.
     * @return Respuesta sin contenido (HTTP 200 OK).
     */
    // Cambiar el estado de un proyecto
    @PutMapping("/proyecto/{idProyecto}/estado")
    public ResponseEntity<Void> cambiarEstadoProyecto(@PathVariable Long idProyecto, @RequestBody CambioEstadoDto cambioEstadoDto) {
        coordinadorService.cambiarEstadoProyecto(idProyecto, cambioEstadoDto);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint para buscar un coordinador por nombre de usuario y contraseña.
     * Método: GET
     * URL: /api/coordinador/buscar/{nombreUsuario}/{contrasenaUsuario}
     *
     */
    @PostMapping("/buscar")
    public ResponseEntity<Coordinador> buscarCoordinadorPorNombreYContrasena(@RequestBody Map<String, String> credenciales) {
        String username = credenciales.get("username");
        String password = credenciales.get("password");
        Coordinador coordinador = coordinadorService.buscarCoordinador(username, password);
        if (coordinador != null) {
            return ResponseEntity.ok(coordinador);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
