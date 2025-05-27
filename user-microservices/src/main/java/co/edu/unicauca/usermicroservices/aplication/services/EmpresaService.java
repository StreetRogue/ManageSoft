package co.edu.unicauca.usermicroservices.aplication.services;


import co.edu.unicauca.usermicroservices.domain.model.Empresa;
import co.edu.unicauca.usermicroservices.domain.repository.EmpresaRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepositoryPort empresaRepository;

    public List<Empresa> getAllEmpresas() {
        return empresaRepository.findAll();  // Devuelve todas las empresas de la base de datos
    }

    public void findById(Long id) {
        empresaRepository.findById(id);
    }

}
