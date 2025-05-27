package co.edu.unicauca.usermicroservices.infrastructure.input.rest;

import co.edu.unicauca.usermicroservices.domain.model.Empresa;
import co.edu.unicauca.usermicroservices.aplication.services.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    // Endpoint para obtener todas las empresas
    @GetMapping("/list")
    public List<Empresa> getAllEmpresas() {
        return empresaService.getAllEmpresas();  // Llamamos al servicio para obtener las empresas
    }
}
