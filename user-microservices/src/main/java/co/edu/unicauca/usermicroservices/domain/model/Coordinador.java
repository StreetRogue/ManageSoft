package co.edu.unicauca.usermicroservices.domain.model;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@DiscriminatorValue("COORDINADOR")
public class Coordinador  extends Usuario {


    @Column(unique = true, nullable = false)
    private int idCoordinador;
    @Column(nullable = false)
    private String nombreCoordinador;
    @Column(nullable = false)
    private String apellidoCoordinador;
    @Column(nullable = false, unique = true)
    private String emailCoordinador;
    @Column(nullable = false, unique = true)
    private String telefonoCoordinador;

}
