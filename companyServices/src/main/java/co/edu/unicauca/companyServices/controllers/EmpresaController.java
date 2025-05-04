package co.edu.unicauca.companyServices.controllers;



import co.edu.unicauca.companyServices.dtos.EmpresaDTO;
import co.edu.unicauca.companyServices.entities.Empresa;
import co.edu.unicauca.companyServices.entities.Proyecto;
import co.edu.unicauca.companyServices.mappers.ProyectoMapper;
import co.edu.unicauca.companyServices.services.EmpresaService;
import co.edu.unicauca.companyServices.services.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {

    @Autowired
    private final EmpresaService empresaService;

    @Autowired
    private final ProyectoService proyectoService;
    @Autowired
    private ProyectoMapper proyectoMapper;

    public EmpresaController(EmpresaService empresaService, ProyectoService proyectoService) {
        this.empresaService = empresaService;
        this.proyectoService = proyectoService;
    }

    @PostMapping
    public ResponseEntity<Empresa> registrarEmpresa(@RequestBody Empresa empresa) {
        return ResponseEntity.ok(empresaService.registrarEmpresa(empresa));
    }

    @GetMapping
    public ResponseEntity<List<Empresa>> listarEmpresas() {
        return ResponseEntity.ok(empresaService.listarEmpresas());
    }

    @GetMapping("/{nit}")
    public ResponseEntity<Empresa> obtenerEmpresa(@PathVariable String nit) {
        Empresa empresa = empresaService.obtenerEmpresaPorNit(nit);
        return empresa != null ? ResponseEntity.ok(empresa) : ResponseEntity.notFound().build();
    }

    @PostMapping("/{nit}/proyectos")
    public ResponseEntity<Proyecto> crearProyecto(@PathVariable String nit, @RequestBody Proyecto proyecto) {
        return ResponseEntity.ok(empresaService.crearProyecto(nit, proyecto));
    }

    @GetMapping("/{nit}/proyectos")
    public ResponseEntity<List<Proyecto>> listarProyectosEmpresa(@PathVariable String nit) {
        return ResponseEntity.ok(empresaService.listarProyectosPorEmpresa(nit));
    }

    // Endpoint para comunicación síncrona con CoordinationService

    @PostMapping("/login")
    public ResponseEntity<EmpresaDTO> BuscarEmpresa(@RequestBody Map<String, String> credenciales) {
        String username = credenciales.get("username");
        String password = credenciales.get("password");

        Empresa empresa = empresaService.buscarPorUsernameYPassword(username, password);
        EmpresaDTO empresita = empresaService.convertirAEmpresaDTO(empresa);

        if (empresa == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Muy útil para debug
        }

        return ResponseEntity.ok(empresita);
    }


    @PostMapping("/login/user")
    public ResponseEntity<Empresa> BuscarEmpresaUser(@PathVariable String credenciales) {
        Empresa empresa = empresaService.buscarPorUsername(credenciales);
        return ResponseEntity.ok(empresa);
    }
}
