package co.edu.unicauca.usermicroservices.services;


import co.edu.unicauca.usermicroservices.repositories.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;


    public void findById(Long id) {
        empresaRepository.findById(id);
    }


}
