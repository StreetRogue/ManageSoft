package co.edu.unicauca.usermicroservices.services;


import co.edu.unicauca.usermicroservices.repositories.CoordinadorRepository;
import co.edu.unicauca.usermicroservices.repositories.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoordinadorService {

    @Autowired
    private CoordinadorRepository coordinadorRepository;


    public void findById(Long id) {
        coordinadorRepository.findById(id);
    }

}
