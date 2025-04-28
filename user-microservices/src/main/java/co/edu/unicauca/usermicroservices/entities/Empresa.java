package co.edu.unicauca.usermicroservices.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
public class Empresa extends Usuario {


    @Column(name = "nit")
    private String nitEmpresa;
    @Column(nullable = false)
    private String nombreEmpresa;
    @Column(nullable = false, unique = true)
    private String emailEmpresa;
    @Column(nullable = false)
    private String sectorEmpresa;
    @Column(nullable = false)
    private String contactoEmpresa;
    @Column(nullable = false)
    private String nombreContactoEmpresa;
    @Column(nullable = false)
    private String apellidoContactoEmpresa;
    @Column(nullable = false)
    private String cargoContactoEmpresa;


}
