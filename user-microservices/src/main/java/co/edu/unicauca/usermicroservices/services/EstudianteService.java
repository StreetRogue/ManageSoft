package co.edu.unicauca.usermicroservices.services;

import co.edu.unicauca.usermicroservices.repositories.EmpresaRepository;
import co.edu.unicauca.usermicroservices.repositories.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;


    public void findById(Long id) {
        estudianteRepository.findById(id);
    }


}
