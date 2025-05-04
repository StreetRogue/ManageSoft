package co.edu.unicauca.companyServices.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "proyectos")
@Getter @Setter @NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idProyecto"
)
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProyecto;

    @Column(nullable = false, unique = true)
    private String nombreProyecto;
    private String resumenProyecto;
    private String objetivoProyecto;
    private String descripcionProyecto;
    private String maximoMesesProyecto;
    private String presupuestoProyecto;

    private LocalDate fechaPublicacionProyecto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoProyecto estadoProyecto = EstadoProyecto.RECIBIDO;

    @JsonIgnore
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nitEmpresa", referencedColumnName = "nitEmpresa")
    private Empresa empresa;

    // Constructor sin empresa para facilitar creación
    public Proyecto(String nombre, String descripcion, String duracionMeses, String presupuesto) {
        this.nombreProyecto = nombre;
        this.descripcionProyecto = descripcion;
        this.maximoMesesProyecto = duracionMeses;
        this.presupuestoProyecto = presupuesto;
    }
}