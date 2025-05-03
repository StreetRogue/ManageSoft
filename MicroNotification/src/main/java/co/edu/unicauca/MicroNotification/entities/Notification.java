package co.edu.unicauca.MicroNotification.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "solicitud_proyecto")
@IdClass(NotificationId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @Column(name = "emailEstudiante")
    private String emailEstudiante;

    @Id
    @Column(name = "emailCoordinador")
    private String emailCoordinador;

    @Id
    @Column(name = "idProyecto")
    private Long idProyecto;

    @Column(name = "asunto")
    private String asunto;

    @Column(name = "motivo")
    private String motivo;

    @Column(name = "estado")
    private String estado;


}
