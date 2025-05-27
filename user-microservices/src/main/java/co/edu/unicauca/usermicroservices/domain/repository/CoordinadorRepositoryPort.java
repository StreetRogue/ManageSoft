package co.edu.unicauca.usermicroservices.domain.repository;

import co.edu.unicauca.usermicroservices.domain.model.Coordinador;
import java.util.List;
import java.util.Optional;

public interface CoordinadorRepositoryPort {
    Optional<Coordinador> findById(Long id);
    List<Coordinador> findAll();
}
