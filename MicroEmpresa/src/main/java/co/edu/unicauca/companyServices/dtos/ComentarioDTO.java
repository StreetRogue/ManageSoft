package co.edu.unicauca.companyServices.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@Builder
public class ComentarioDTO {

    private String comentario;
    private String emailCoordinador;
    private String emailEmpresa;
    private Long idProyecto;

    // Constructor adicional si es necesario
    public ComentarioDTO(String comentario, String emailCoordinador, String emailEmpresa, Long idProyecto) {
        this.comentario = comentario;
        this.emailCoordinador = emailCoordinador;
        this.emailEmpresa = emailEmpresa;
        this.idProyecto = idProyecto;
    }
}