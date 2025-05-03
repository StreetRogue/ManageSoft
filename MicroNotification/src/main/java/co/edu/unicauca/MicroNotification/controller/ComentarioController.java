    package co.edu.unicauca.MicroNotification.controller;

    import co.edu.unicauca.MicroNotification.entities.Comentario;
    import co.edu.unicauca.MicroNotification.infra.dto.ComentarioDTO;
    import co.edu.unicauca.MicroNotification.services.ComentarioService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("api/comentarios")
    @RequiredArgsConstructor
    public class ComentarioController {

        private final ComentarioService service;

        @PostMapping
        public ResponseEntity<String> recibirComentario(@RequestBody ComentarioDTO dto) {
            service.guardarComentario(dto); // Ya no devuelve nada
            return ResponseEntity.status(HttpStatus.CREATED).body("Comentario recibido y procesado.");
        }
    }
