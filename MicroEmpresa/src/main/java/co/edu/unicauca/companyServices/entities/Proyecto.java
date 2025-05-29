package co.edu.unicauca.companyServices.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<HistorialProyecto> historial = new ArrayList<>();

    // Constructor sin empresa para facilitar creación
    public Proyecto(String nombre, String descripcion, String duracionMeses, String presupuesto) {
        this.nombreProyecto = nombre;
        this.descripcionProyecto = descripcion;
        this.maximoMesesProyecto = duracionMeses;
        this.presupuestoProyecto = presupuesto;
    }


}