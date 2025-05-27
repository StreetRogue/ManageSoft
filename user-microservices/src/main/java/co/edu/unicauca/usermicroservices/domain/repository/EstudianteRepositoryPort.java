package co.edu.unicauca.usermicroservices.domain.repository;

import co.edu.unicauca.usermicroservices.domain.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface EstudianteRepositoryPort {
    Optional<Estudiante> findById(Long id);
    List<Estudiante> findAll();
}
