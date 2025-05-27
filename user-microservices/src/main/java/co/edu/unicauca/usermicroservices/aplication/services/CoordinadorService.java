package co.edu.unicauca.usermicroservices.aplication.services;


import co.edu.unicauca.usermicroservices.domain.model.Coordinador;
import co.edu.unicauca.usermicroservices.domain.repository.CoordinadorRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CoordinadorService {

    private final CoordinadorRepositoryPort coordinadorRepository;

    public CoordinadorService(CoordinadorRepositoryPort coordinadorRepository) {
        this.coordinadorRepository = coordinadorRepository;
    }

    public Optional<Coordinador> findById(Long id) {
        return coordinadorRepository.findById(id);
    }

}
