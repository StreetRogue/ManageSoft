package co.edu.unicauca.microestudiante.access;

import co.edu.unicauca.microestudiante.entities.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProyectoRepositorio extends JpaRepository<Proyecto, Long> {
}
