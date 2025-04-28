package co.edu.unicauca.microestudiante.controller;


import co.edu.unicauca.microestudiante.services.EstudianteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/estudiantes")
public class EstudianteController {
    private EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @PostMapping("/{idEstudiante}/{idProyecto}")
    public ResponseEntity<?> PostulationCreatedEvent(@PathVariable Long idEstudiante, @PathVariable Long idProyecto) throws Exception {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(estudianteService.postulacion(idEstudiante, idProyecto));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente mas tarde.\"}");
        }
    }
}
