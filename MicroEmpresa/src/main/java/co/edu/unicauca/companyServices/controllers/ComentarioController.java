package co.edu.unicauca.companyServices.controllers;

import co.edu.unicauca.companyServices.entities.Comentario;
import co.edu.unicauca.companyServices.dtos.ComentarioDTO;
import co.edu.unicauca.companyServices.services.ComentarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ComentarioController {

    private final ComentarioService service;

    @PostMapping
    public ResponseEntity<String> recibirComentario(@RequestBody ComentarioDTO dto) {
        service.guardarComentario(dto); // Ya no devuelve nada
        return ResponseEntity.status(HttpStatus.CREATED).body("Comentario recibido y procesado.");
    }

    @GetMapping
    public ResponseEntity<List<Comentario>> obtenerComentarios() {
        return ResponseEntity.ok(service.mostrarComentarios());
    }

    @GetMapping("/contador")
    public ResponseEntity<Integer> contarPorEmailCoordinador(@RequestParam String email) {
        int total = service.contarComentariosPorEmailCoordinador(email);
        return ResponseEntity.ok(total);
    }
}
