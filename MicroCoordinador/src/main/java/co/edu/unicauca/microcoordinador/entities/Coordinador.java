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
    @Id
    @Column(name = "id_coordinador")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCoordinador;

    @Column(name = "codigo_simca", unique = true)
    private String codigoSimca;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "contrasena")
    private String contrasena;

    @OneToMany(mappedBy = "coordinador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Proyecto> proyectosAsignados = new ArrayList<>();

    public Coordinador() {
    }

    public Coordinador(String codigoSimca, String nombre, String apellido,
                       String email, String telefono, String contrasena) {
        this.codigoSimca = codigoSimca;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.contrasena = contrasena;
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