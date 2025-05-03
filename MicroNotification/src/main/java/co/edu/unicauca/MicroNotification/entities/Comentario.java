package co.edu.unicauca.MicroNotification.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comentario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "email_coordinador")
    private String emailCoordinador;

    @Column(name = "email_empresa")
    private String emailEmpresa;

    @Column(name = "id_proyecto")
    private Long idProyecto;

    // Constructor adicional si es necesario
    public Comentario(String comentario, String emailCoordinador, String emailEmpresa, Long idProyecto) {
        this.comentario = comentario;
        this.emailCoordinador = emailCoordinador;
        this.emailEmpresa = emailEmpresa;
        this.idProyecto = idProyecto;
    }
}
