package co.edu.unicauca.companyServices.repositories;


import co.edu.unicauca.companyServices.entities.EstadoProyecto;
import co.edu.unicauca.companyServices.entities.Proyecto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    List<Proyecto> findByEmpresaNitEmpresa(String nitEmpresa);
    List<Proyecto> findByEstadoProyecto(EstadoProyecto estado);
    long countByEstadoProyecto(EstadoProyecto estadoProyecto);

    @Query("SELECT COUNT(p) FROM Proyecto p WHERE p.estadoProyecto = :estado AND p.fechaPublicacionProyecto BETWEEN :inicio AND :fin")
    long contarPorEstadoYPeriodo(@Param("estado") EstadoProyecto estado,
                                 @Param("inicio") LocalDate inicio,
                                 @Param("fin") LocalDate fin);
    @Query(value = """
    SELECT AVG(
        EXTRACT(
            DAY FROM (
                (SELECT hp.fecha_cambio 
                 FROM historial_proyecto hp 
                 WHERE hp.id_proyecto = p.id_proyecto 
                   AND hp.estado = 'ACEPTADO'
                   AND hp.fecha_cambio BETWEEN :inicio AND :fin
                 ORDER BY hp.fecha_cambio ASC 
                 LIMIT 1
                ) - p.fecha_publicacion_proyecto
            )
        )::integer
    )
    FROM proyectos p
    WHERE p.id_proyecto IN (
        SELECT DISTINCT hp.id_proyecto 
        FROM historial_proyecto hp 
        WHERE hp.estado = 'ACEPTADO'
          AND hp.fecha_cambio BETWEEN :inicio AND :fin
    )
""", nativeQuery = true)
    Integer promedioDiasAceptado(@Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);

    @EntityGraph(attributePaths = {"empresa", "historial"})
    Optional<Proyecto> findWithEmpresaAndHistorialByIdProyecto(Long idProyecto);
}

