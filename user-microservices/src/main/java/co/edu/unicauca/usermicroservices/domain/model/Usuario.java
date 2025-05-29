package co.edu.unicauca.usermicroservices.domain.model;

import co.edu.unicauca.usermicroservices.serializers.UsuarioDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonDeserialize(using = UsuarioDeserializer.class)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;

    @Column(unique = true, nullable = false)
    private String nombreUsuario;
    @Column(nullable = false)
    private String contrasenaUsuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario")
    private enumTipoUsuario tipoUsuario;

    private String token;





}
