package co.edu.unicauca.usermicroservices.domain.repository;


import co.edu.unicauca.usermicroservices.domain.model.Usuario;

import java.util.List;

public interface UsuarioRepositoryPort {
    List<Usuario> findAll();
    <S extends Usuario> S save(S usuario);//tengo mis dudas
    Usuario findByNombreUsuario(String nombreUsuario);
    Usuario findByNombreUsuarioAndContrasenaUsuario(String nombreUsuario, String contrasenaUsuario);
}
