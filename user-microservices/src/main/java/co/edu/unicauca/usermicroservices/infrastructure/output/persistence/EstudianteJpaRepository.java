package co.edu.unicauca.usermicroservices.infrastructure.output.persistence;

import co.edu.unicauca.usermicroservices.domain.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteJpaRepository extends JpaRepository<Estudiante, Long> {
}
