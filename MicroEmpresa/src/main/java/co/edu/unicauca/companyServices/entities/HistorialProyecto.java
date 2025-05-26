package co.edu.unicauca.companyServices.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter @NoArgsConstructor
public class HistorialProyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHistorial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProyecto", nullable = false)
    private Proyecto proyecto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoProyecto estado;

    @Column(nullable = false)
    private LocalDateTime fechaCambio = LocalDateTime.now();

    public HistorialProyecto(Proyecto proyecto, EstadoProyecto estado) {
        this.proyecto = proyecto;
        this.estado = estado;
        this.fechaCambio = LocalDateTime.now();
    }
}
