package co.edu.unicauca.microestudiante.controller;

import co.edu.unicauca.microestudiante.entities.Estudiante;
import co.edu.unicauca.microestudiante.services.EstudianteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/estudiante")
public class EstudianteController {
    private EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @PostMapping
    public ResponseEntity<?> RegistrarEstudianteCreatedEvent(@RequestBody Estudiante estudiante) throws Exception{
        try {
            return ResponseEntity.status(HttpStatus.OK).body(estudianteService.registrarEstudiante(estudiante));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> BuscarEstudianteCreatedEvent(@RequestBody Map<String, String> credenciales) throws Exception {
        String username = credenciales.get("username");
        String password = credenciales.get("password");

        try {
            return ResponseEntity.status(HttpStatus.OK).body(estudianteService.buscarEstudiante(username, password));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> ListarEstudianteCreatedEvent() throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(estudianteService.listarEstudiantes());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/{idEstudiante}/{idProyecto}")
    public ResponseEntity<?> PostulationCreatedEvent(@PathVariable Long idEstudiante, @PathVariable Long idProyecto) throws Exception {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(estudianteService.postulacion(idEstudiante, idProyecto));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/total")
    public ResponseEntity<Integer> contarEstudiantes() {
        Integer total = estudianteService.contarEstudiantes();
        return ResponseEntity.ok(total != null ? total : 0);

    }
}
