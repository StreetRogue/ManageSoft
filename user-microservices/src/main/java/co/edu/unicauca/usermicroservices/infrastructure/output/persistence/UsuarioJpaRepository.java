package co.edu.unicauca.usermicroservices.infrastructure.output.persistence;

import co.edu.unicauca.usermicroservices.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioJpaRepository extends JpaRepository<Usuario, Long> {
    Usuario findByNombreUsuario(String nombreUsuario);
    Usuario findByNombreUsuarioAndContrasenaUsuario(String nombreUsuario, String contrasenaUsuario);
}