package co.edu.unicauca.usermicroservices.infrastructure.output.persistence;

import co.edu.unicauca.usermicroservices.domain.model.Empresa;
import co.edu.unicauca.usermicroservices.domain.repository.EmpresaRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EmpresaRepositoryAdapter implements EmpresaRepositoryPort {

    private final EmpresaJpaRepository empresaJpaRepository;

    public EmpresaRepositoryAdapter(EmpresaJpaRepository empresaJpaRepository) {
        this.empresaJpaRepository = empresaJpaRepository;
    }

    @Override
    public Optional<Empresa> findById(Long id) {
        return empresaJpaRepository.findById(id);
    }

    @Override
    public List<Empresa> findAll() {
        return empresaJpaRepository.findAll();
    }
}
