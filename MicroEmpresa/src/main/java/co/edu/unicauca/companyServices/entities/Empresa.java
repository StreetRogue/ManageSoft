    package co.edu.unicauca.companyServices.entities;

    import com.fasterxml.jackson.annotation.JsonIdentityInfo;
    import com.fasterxml.jackson.annotation.JsonIgnore;
    import com.fasterxml.jackson.annotation.JsonManagedReference;
    import com.fasterxml.jackson.annotation.ObjectIdGenerators;
    import jakarta.persistence.*;
    import lombok.*;

    import java.util.ArrayList;
    import java.util.List;

    @Entity
    @Table(name = "empresas")
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "nitEmpresa"
    )
    public class Empresa {
        @Id
        @Column(name = "nitEmpresa")
        private String nitEmpresa;

        @Column(nullable = false)
        private String nombreEmpresa;

        @Column(nullable = false, unique = true)
        private String emailEmpresa;

        private String sectorEmpresa;
        private String contactoEmpresa;
        private String nombreContactoEmpresa;
        private String apellidoContactoEmpresa;
        private String cargoContactoEmpresa;

        // Campos de usuario
        @Column(nullable = false, unique = true)
        private String nombreUsuario;

        @Column(nullable = false)
        private String contrasenaUsuario;

        @Enumerated(EnumType.STRING)
        private TipoUsuario tipoUsuario = TipoUsuario.EMPRESA;

        @JsonManagedReference
        @OneToMany(
                mappedBy = "empresa",
                cascade = CascadeType.ALL,
                orphanRemoval = true,
                fetch = FetchType.EAGER
        )
        private List<Proyecto> proyectos = new ArrayList<>();


        // Métodos helper para manejar la relación bidireccional
        public void addProyecto(Proyecto proyecto) {
            proyectos.add(proyecto);
            proyecto.setEmpresa(this);
        }

        public void removeProyecto(Proyecto proyecto) {
            proyectos.remove(proyecto);
            proyecto.setEmpresa(null);
        }
    }