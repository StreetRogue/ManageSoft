package co.edu.unicauca.microestudiante.access;

import co.edu.unicauca.microestudiante.entities.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEstudianteRepositorio extends JpaRepository<Estudiante, Long> {
}
