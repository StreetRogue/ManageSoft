package co.edu.unicauca.usermicroservices.repositories;

import co.edu.unicauca.usermicroservices.entities.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
}
