package co.edu.unicauca.usermicroservices.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipoUsuario")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Estudiante.class, name = "ESTUDIANTE"),
        @JsonSubTypes.Type(value = Coordinador.class, name = "COORDINADOR"),
        @JsonSubTypes.Type(value = Empresa.class, name = "EMPRESA")
})
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private int idUsuario;

    private String nombreUsuario;
    private String contrasena;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario")
    private enumTipoUsuario tipoUsuario;




}
