package co.edu.unicauca.MicroNotification.controller;

import co.edu.unicauca.MicroNotification.entities.Notification;
import co.edu.unicauca.MicroNotification.infra.dto.NotificationDTO;
import co.edu.unicauca.MicroNotification.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/notificaciones")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService service;

    @PostMapping
    public ResponseEntity<?> recibirNotificacion(@RequestBody NotificationDTO dto) {
        try {
            service.guardarNotificacion(dto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar la notificación");
        }
    }
}
