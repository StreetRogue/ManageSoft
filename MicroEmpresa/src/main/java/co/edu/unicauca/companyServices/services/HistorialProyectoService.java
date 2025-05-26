package co.edu.unicauca.companyServices.services;

import co.edu.unicauca.companyServices.dtos.HistorialProyectoDTO;
import co.edu.unicauca.companyServices.entities.HistorialProyecto;
import co.edu.unicauca.companyServices.repositories.HistorialProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistorialProyectoService {
    @Autowired
    private HistorialProyectoRepository historialRepo;

    public List<HistorialProyectoDTO> obtenerHistorialPorProyecto(Long idProyecto) {
        List<HistorialProyecto> historial = historialRepo.findByProyecto_IdProyectoOrderByFechaCambioAsc(idProyecto);

        return historial.stream()
                .map(h -> new HistorialProyectoDTO(h.getEstado(), h.getFechaCambio()))
                .collect(Collectors.toList());
    }
}
