package co.edu.unicauca.usermicroservices.infrastructure.output.persistence;

import co.edu.unicauca.usermicroservices.domain.model.Usuario;
import co.edu.unicauca.usermicroservices.domain.repository.UsuarioRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {
    private final UsuarioJpaRepository usuarioJpaRepository;

    public UsuarioRepositoryAdapter(UsuarioJpaRepository usuarioJpaRepository) {
        this.usuarioJpaRepository = usuarioJpaRepository;
    }

    @Override
    public Usuario findByNombreUsuario(String nombreUsuario) {
        return usuarioJpaRepository.findByNombreUsuario(nombreUsuario);
    }

    @Override
    public Usuario findByNombreUsuarioAndContrasenaUsuario(String nombreUsuario, String contrasenaUsuario) {
        return usuarioJpaRepository.findByNombreUsuarioAndContrasenaUsuario(nombreUsuario, contrasenaUsuario);
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioJpaRepository.findAll();
    }

    @Override
    public <S extends Usuario> S save(S usuario) {
        return usuarioJpaRepository.save(usuario);
    }
}
