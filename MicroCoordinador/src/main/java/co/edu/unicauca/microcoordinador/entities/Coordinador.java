package co.edu.unicauca.microcoordinador.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "coordinador")
public class Coordinador {
//
    @Id
    @Column(name = "id_coordinador")
    private Long idCoordinador;

    @Column(name = "nombreCoordinador")
    private String nombreCoordinador;

    @Column(name = "apellidoCoordinador")
    private String apellidoCoordinador;

    @Column(name = "emailCoordinador", unique = true)
    private String emailCoordinador;

    @Column(name = "telefonoCoordinador")
    private String telefonoCoordinador;

    // Campos de usuario
    @Column(nullable = false, unique = true)
    private String nombreUsuario;

    @Column(nullable = false)
    private String contrasenaUsuario;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario = TipoUsuario.EMPRESA;

    @OneToMany(mappedBy = "coordinador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Proyecto> proyectosAsignados = new ArrayList<>();



    public Coordinador() {}

    public Coordinador(String nombreCoordinador, String emailCoordinador, String telefonoCoordinador) {
        this.nombreCoordinador = nombreCoordinador;
        this.emailCoordinador = emailCoordinador;
        this.telefonoCoordinador = telefonoCoordinador;
    }

    public void asignarProyecto(Proyecto proyecto) {
        proyectosAsignados.add(proyecto);
        proyecto.setCoordinador(this);
    }

    public void removerProyecto(Proyecto proyecto) {
        proyectosAsignados.remove(proyecto);
        proyecto.setCoordinador(null);
    }
}