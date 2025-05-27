package co.edu.unicauca.usermicroservices.domain.repository;

import co.edu.unicauca.usermicroservices.domain.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface EmpresaRepositoryPort{
    Optional<Empresa> findById(Long id);
    List<Empresa> findAll();
}
