package co.edu.unicauca.companyServices.controllers;


import co.edu.unicauca.companyServices.dtos.ProyectoDTO;
import co.edu.unicauca.companyServices.entities.Empresa;
import co.edu.unicauca.companyServices.entities.EstadoProyecto;
import co.edu.unicauca.companyServices.entities.Proyecto;
import co.edu.unicauca.companyServices.mappers.ProyectoMapper;
import co.edu.unicauca.companyServices.services.EmpresaService;
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


    @GetMapping
    public ResponseEntity<List<ProyectoDTO>> getProyectos() {
        List<Proyecto> proyectos = proyectoService.listarProyectos();
        List<ProyectoDTO> proyectosDTO = proyectos.stream()
                .map(proyectoMapper::toProyectoDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(proyectosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProyectoDTO> getProyecto(@PathVariable Long id) {
        Proyecto proyecto = proyectoService.findById(id);
        return ResponseEntity.ok(proyectoMapper.toProyectoDTO(proyecto));
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
}