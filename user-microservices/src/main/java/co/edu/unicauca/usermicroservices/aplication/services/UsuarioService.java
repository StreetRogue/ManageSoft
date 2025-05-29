package co.edu.unicauca.usermicroservices.aplication.services;


import co.edu.unicauca.usermicroservices.domain.model.*;
import co.edu.unicauca.usermicroservices.domain.repository.UsuarioRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepositoryPort usuarioRepository;

    @Autowired
    private KeycloackService keycloakAdminService;

    public Iterable<Usuario> findAllUsers() {
        return usuarioRepository.findAll();
    }

    public Usuario register(Usuario usuario) {
        System.out.println("Tipo de usuario recibido: " + usuario.getTipoUsuario());
        System.out.println("Id usuario: " + usuario.getIdUsuario());

        // Convertir el tipo de usuario de String a enumTipoUsuario (enum)
        if (usuario.getTipoUsuario() != null) {
            enumTipoUsuario tipoUsuarioEnum = enumTipoUsuario.valueOf(String.valueOf(usuario.getTipoUsuario()));
            usuario.setTipoUsuario(tipoUsuarioEnum);


            keycloakAdminService.createUserInKeycloak(usuario);

            switch (tipoUsuarioEnum) {  // Usamos el enum en lugar del String
                case ESTUDIANTE:
                    return usuarioRepository.save((Estudiante) usuario);  // Guardamos como Estudiante
                case COORDINADOR:
                    return usuarioRepository.save((Coordinador) usuario);  // Guardamos como Coordinador
                case EMPRESA:
                    return usuarioRepository.save((Empresa) usuario);  // Guardamos como Empresa
                default:
                    throw new IllegalArgumentException("Tipo de usuario desconocido");
            }

        }
        keycloakAdminService.createUserInKeycloak(usuario);

        return null;
    }

    // Metodo para login de usuario
    public Usuario login(String nombreUsuario, String contrasenaUsuario) {

        Usuario usuario = usuarioRepository.findByNombreUsuarioAndContrasenaUsuario(nombreUsuario, contrasenaUsuario);

        if (usuario != null && usuario.getContrasenaUsuario().equals(contrasenaUsuario)) {
            return usuario;
        }
        return null;
    }
}
