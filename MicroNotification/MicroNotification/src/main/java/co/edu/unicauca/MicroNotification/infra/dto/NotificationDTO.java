package co.edu.unicauca.MicroNotification.infra.dto;

import lombok.*;

@Data
@NoArgsConstructor
@Builder
public class NotificationDTO {

    private String emailEstudiante;
    private String emailCoordinador;
    private Long idProyecto;
    private String asunto;
    private String motivo;
    private String estado;

    public NotificationDTO(String emailEstudiante,String emailCoordinador, Long idProyecto, String asunto, String motivo, String estado) {
        this.emailEstudiante = emailEstudiante;
        this.emailCoordinador = emailCoordinador;
        this.idProyecto = idProyecto;
        this.asunto = asunto;
        this.motivo = motivo;
        this.estado = estado;
    }
}
