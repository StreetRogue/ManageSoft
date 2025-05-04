package co.edu.unicauca.microcoordinador.access.repositories;

import co.edu.unicauca.microcoordinador.entities.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProyectoRepositorio extends JpaRepository<Proyecto, Long> {
}
