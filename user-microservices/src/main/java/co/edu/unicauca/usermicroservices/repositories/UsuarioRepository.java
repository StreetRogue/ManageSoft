package co.edu.unicauca.usermicroservices.repositories;


import co.edu.unicauca.usermicroservices.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByNombreUsuario(String nombreUsuario);
    Usuario findByNombreUsuarioAndContrasena(String nombreUsuario, String contrasena);
}
