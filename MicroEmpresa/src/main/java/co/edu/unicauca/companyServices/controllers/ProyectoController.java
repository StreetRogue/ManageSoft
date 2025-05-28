package co.edu.unicauca.companyServices.controllers;


import co.edu.unicauca.companyServices.dtos.HistorialProyectoDTO;
import co.edu.unicauca.companyServices.dtos.ProyectoDTO;
import co.edu.unicauca.companyServices.dtos.ProyectoDetalleDTO;
import co.edu.unicauca.companyServices.entities.Empresa;
import co.edu.unicauca.companyServices.entities.EstadoProyecto;
import co.edu.unicauca.companyServices.entities.Proyecto;
import co.edu.unicauca.companyServices.mappers.ProyectoMapper;
import co.edu.unicauca.companyServices.services.EmpresaService;
import co.edu.unicauca.companyServices.services.HistorialProyectoService;
import co.edu.unicauca.companyServices.services.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/proyectos")
@CrossOrigin(origins = "*")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;


    @Autowired
    private ProyectoMapper proyectoMapper;

    @Autowired
    private HistorialProyectoService historialService;


    @GetMapping
    public ResponseEntity<List<ProyectoDTO>> getProyectos() {
        List<Proyecto> proyectos = proyectoService.listarProyectos();
        List<ProyectoDTO> proyectosDTO = proyectos.stream()
                .map(proyectoMapper::toProyectoDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(proyectosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> getProyecto(@PathVariable Long id) {
        Proyecto proyecto = proyectoService.findById(id);
        return ResponseEntity.ok(proyecto);
    }
    @GetMapping("/{id}/def")
    public ResponseEntity<ProyectoDetalleDTO> getProyectodef(@PathVariable Long id) {
        ProyectoDetalleDTO dto = proyectoService.obtenerDetalleProyecto(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<ProyectoDTO> actualizarEstado(
            @PathVariable Long id,
            @RequestBody EstadoProyecto nuevoEstado) {

        Proyecto proyecto = proyectoService.actualizarEstado(id, nuevoEstado);
        return ResponseEntity.ok(proyectoMapper.toProyectoDTO(proyecto));
    }
/*
    private ProyectoDTO convertToDto(Proyecto proyecto) {
        ProyectoDTO dto = new ProyectoDTO();
        dto.setId(proyecto.getIdProyecto());
        dto.setNombre(proyecto.getNombre());
        dto.setDescripcion(proyecto.getDescripcion());
        dto.setEstado(proyecto.getEstado().name());
        dto.setFechaPublicacion(proyecto.getFechaPublicacion());

        if(proyecto.getEmpresa() != null) {
            dto.setEmpresaNit(proyecto.getEmpresa().getNit());
        }

        return dto;
    }
}
*/

    @GetMapping("/contador")
    public ResponseEntity<?> contarPorEstado(@RequestParam String estado) {
        try {
            EstadoProyecto estadoEnum = EstadoProyecto.valueOf(estado.toUpperCase());
            long cantidad = proyectoService.contarProyectosPorEstado(estadoEnum);
            return ResponseEntity.ok(cantidad);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Estado no válido. Usa uno de: RECIBIDO, ACEPTADO, RECHAZADO, EN_EJECUCION, CERRADO");
        }
    }

    @GetMapping("/contador2")
    public ResponseEntity<?> contarPorEstadoYPeriodo(@RequestParam String estado,
                                                     @RequestParam String periodo) {
        try {
            long cantidad = proyectoService.contarPorEstadoYPeriodo(estado, periodo);
            return ResponseEntity.ok(cantidad);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/historial")
    public ResponseEntity<List<HistorialProyectoDTO>> obtenerHistorial(@PathVariable Long id) {
        List<HistorialProyectoDTO> historial = historialService.obtenerHistorialPorProyecto(id);
        return ResponseEntity.ok(historial);
    }

    @GetMapping("/estadisticas/promedio-aceptacion")
    public ResponseEntity<Integer> promedioDiasAceptacion(@RequestParam String periodo) {
        Integer promedio = proyectoService.obtenerPromedioDiasAceptacion(periodo);
        return ResponseEntity.ok(promedio != null ? promedio : 0);
    }
}