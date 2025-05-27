package co.edu.unicauca.usermicroservices.infrastructure.output.persistence;

import co.edu.unicauca.usermicroservices.domain.model.Estudiante;
import co.edu.unicauca.usermicroservices.domain.repository.EstudianteRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EstudianteRepositoryAdapter implements EstudianteRepositoryPort {
    private final EstudianteJpaRepository estudianteJpaRepository;

    public EstudianteRepositoryAdapter(EstudianteJpaRepository estudianteJpaRepository) {
        this.estudianteJpaRepository = estudianteJpaRepository;
    }

    @Override
    public Optional<Estudiante> findById(Long id) {
        return estudianteJpaRepository.findById(id);
    }

    @Override
    public List<Estudiante> findAll() {
        return estudianteJpaRepository.findAll();
    }

}