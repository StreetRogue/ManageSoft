package co.edu.unicauca.usermicroservices.domain.model;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@DiscriminatorValue("ESTUDIANTE")
public class Estudiante extends Usuario {

    @Column(unique = true, nullable = false)
    private Long codigoSimcaEstudiante;

    private String nombreEstudiante;
    private String apellidoEstudiante;
    private String emailEstudiante;



}
