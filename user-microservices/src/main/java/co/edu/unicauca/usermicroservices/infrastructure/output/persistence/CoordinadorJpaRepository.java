package co.edu.unicauca.usermicroservices.infrastructure.output.persistence;

import co.edu.unicauca.usermicroservices.domain.model.Coordinador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoordinadorJpaRepository extends JpaRepository<Coordinador, Long> {

}
