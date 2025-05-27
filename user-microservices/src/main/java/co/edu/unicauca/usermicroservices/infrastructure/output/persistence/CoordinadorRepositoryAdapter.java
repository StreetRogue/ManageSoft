package co.edu.unicauca.usermicroservices.infrastructure.output.persistence;

import co.edu.unicauca.usermicroservices.domain.model.Coordinador;
import co.edu.unicauca.usermicroservices.domain.repository.CoordinadorRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CoordinadorRepositoryAdapter implements CoordinadorRepositoryPort {

    private final CoordinadorJpaRepository coordinadorJpaRepository;

    public CoordinadorRepositoryAdapter(CoordinadorJpaRepository coordinadorJpaRepository) {
        this.coordinadorJpaRepository = coordinadorJpaRepository;
    }

    @Override
    public Optional<Coordinador> findById(Long id) {
        return coordinadorJpaRepository.findById(id);
    }

    @Override
    public List<Coordinador> findAll() {
        return coordinadorJpaRepository.findAll();
    }

}
