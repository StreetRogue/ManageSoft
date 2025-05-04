package co.edu.unicauca.companyServices.repositories;


import co.edu.unicauca.companyServices.entities.EstadoProyecto;
import co.edu.unicauca.companyServices.entities.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    List<Proyecto> findByEmpresaNitEmpresa(String nitEmpresa);
    List<Proyecto> findByEstadoProyecto(EstadoProyecto estado);
}
