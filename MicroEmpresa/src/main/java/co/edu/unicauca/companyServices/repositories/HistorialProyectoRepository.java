package co.edu.unicauca.companyServices.repositories;

import co.edu.unicauca.companyServices.entities.HistorialProyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialProyectoRepository extends JpaRepository<HistorialProyecto, Long> {

    List<HistorialProyecto> findByProyecto_IdProyectoOrderByFechaCambioAsc(Long idProyecto);
}
