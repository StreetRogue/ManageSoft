package co.edu.unicauca.MicroNotification.entities;

import lombok.*;

import java.io.Serializable;

@Data

public class NotificationId implements Serializable {
    private String emailEstudiante;
    private String emailCoordinador;
    private Long idProyecto;
}