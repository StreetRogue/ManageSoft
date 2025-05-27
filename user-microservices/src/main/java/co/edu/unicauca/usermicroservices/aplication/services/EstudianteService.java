package co.edu.unicauca.usermicroservices.aplication.services;

import co.edu.unicauca.usermicroservices.domain.model.Estudiante;
import co.edu.unicauca.usermicroservices.domain.repository.EstudianteRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EstudianteService {

    private final EstudianteRepositoryPort estudianteRepository;

    public EstudianteService(EstudianteRepositoryPort estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    public Optional<Estudiante> findById(Long id) {
        return estudianteRepository.findById(id);
    }

    // guardar o buscar todos
    /*
    public List<Estudiante> findAll() {
        return estudianteRepository.findAll();
    }

    public Estudiante save(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }
    */

}
