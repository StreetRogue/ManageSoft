package co.edu.unicauca.companyServices.mappers;

import co.edu.unicauca.companyServices.dtos.*;
import co.edu.unicauca.companyServices.entities.Empresa;
import co.edu.unicauca.companyServices.entities.Proyecto;
import org.springframework.stereotype.Component;

@Component
public class ProyectoMapper {

    public ProyectoDTO toProyectoDTO(Proyecto proyecto) {
        ProyectoDTO dto = new ProyectoDTO();
        dto.setId(proyecto.getIdProyecto());
        dto.setNombre(proyecto.getNombreProyecto());
        dto.setResumen(proyecto.getResumenProyecto());
        dto.setObjetivo(proyecto.getObjetivoProyecto());
        dto.setDescripcion(proyecto.getDescripcionProyecto());
        dto.setDuracionMeses(proyecto.getMaximoMesesProyecto());
        dto.setPresupuesto(proyecto.getPresupuestoProyecto());
        dto.setFechaPublicacion(proyecto.getFechaPublicacionProyecto());
        dto.setEstado(proyecto.getEstadoProyecto().name());
        dto.setEmpresa(toEmpresaBasicDTO(proyecto.getEmpresa()));

        return dto;
    }
    /*
    public ProyectoEstudianteDTO toProyectoEstudianteDTO(Proyecto proyecto) {
        ProyectoEstudianteDTO dto = new ProyectoEstudianteDTO();
        dto.setIdProyecto(proyecto.getIdProyecto());
        dto.setNombreProyecto(proyecto.getNombreProyecto());
        dto.setResumenProyecto(proyecto.getResumenProyecto());
        dto.setObjetivoProyecto(proyecto.getObjetivoProyecto());
        dto.setDescripcionProyecto(proyecto.getDescripcionProyecto());
        dto.setMaximoMesesProyecto(proyecto.getMaximoMesesProyecto());
        dto.setPresupuestoProyecto(proyecto.getPresupuestoProyecto());
        dto.setFechaPublicacionProyecto(proyecto.getFechaPublicacionProyecto().toString());
        dto.setEstadoProyecto(proyecto.getEstadoProyecto());
        dto.setEstudiantesPostulados(proyecto.getEstudiantesPostulados().stream().map(Estudiante::getCodigoSimcaEstudiante).collect(Collectors.toList()) );
        dto.setEstudiantesAceptados(proyecto.getEstudiantesAceptados().stream().map(Estudiante::getCodigoSimcaEstudiante).collect(Collectors.toList()) );
        return dto;
    }
    */

    public EmpresaBasicDTO toEmpresaBasicDTO(Empresa empresa) {
        EmpresaBasicDTO dto = new EmpresaBasicDTO();
        dto.setNitEmpresa(empresa.getNitEmpresa());
        dto.setNombreEmpresa(empresa.getNombreEmpresa());
        dto.setEmailEmpresa(empresa.getEmailEmpresa());
        dto.setSectorEmpresa(empresa.getSectorEmpresa());
        dto.setTelefonoContactoEmpresa(empresa.getContactoEmpresa());
        dto.setNombreContactoEmpresa(empresa.getNombreContactoEmpresa());
        dto.setApellidoContactoEmpresa(empresa.getApellidoContactoEmpresa());
        dto.setCargoContactoEmpresa(empresa.getCargoContactoEmpresa());

        return dto;
    }

    private ProyectoBasicDTO toProyectoBasicDTO(Proyecto proyecto) {
        ProyectoBasicDTO dto = new ProyectoBasicDTO();
        dto.setId(proyecto.getIdProyecto());
        dto.setNombre(proyecto.getNombreProyecto());
        dto.setEstado(proyecto.getEstadoProyecto().name());
        return dto;
    }
}