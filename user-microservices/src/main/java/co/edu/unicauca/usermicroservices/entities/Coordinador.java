package co.edu.unicauca.usermicroservices.entities;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
public class Coordinador  extends Usuario{



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
