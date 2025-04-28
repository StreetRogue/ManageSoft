package co.edu.unicauca.usermicroservices.services;


import co.edu.unicauca.usermicroservices.entities.Coordinador;
import co.edu.unicauca.usermicroservices.entities.Empresa;
import co.edu.unicauca.usermicroservices.entities.Estudiante;
import co.edu.unicauca.usermicroservices.entities.Usuario;
import co.edu.unicauca.usermicroservices.repositories.CoordinadorRepository;
import co.edu.unicauca.usermicroservices.repositories.EmpresaRepository;
import co.edu.unicauca.usermicroservices.repositories.EstudianteRepository;
import co.edu.unicauca.usermicroservices.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {


    @Autowired
    private UsuarioRepository usuarioRepository;


    // Metodo para registrar un usuario
    public Usuario register(Usuario usuario) {
        System.out.println("Tipo de usuario recibido: " + usuario.getClass().getName());
        System.out.println(usuario.getTipoUsuario());
        if (usuario instanceof Estudiante) {
            // Crear Estudiante
            Estudiante estudiante = (Estudiante) usuario;
            System.out.println(estudiante.getIdUsuario());
            System.out.println(estudiante.getNombreUsuario());
            return usuarioRepository.save(estudiante);
        } else if (usuario instanceof Coordinador) {
            // Crear Coordinador
            Coordinador coordinador = (Coordinador) usuario;
            return usuarioRepository.save(coordinador);
        } else if (usuario instanceof Empresa) {
            // Crear Empresa
            Empresa empresa = (Empresa) usuario;
            return usuarioRepository.save(empresa);
        } else {
            System.out.println("ERROR: Tipo de usuario desconocido");
            return null;
        }
    }

    // Metodo para login de usuario
    public Usuario login(String nombreUsuario, String contrasenaUsuario) {
        // Buscar el usuario en la base de datos por nombre de usuario y contraseña
        Usuario usuario = usuarioRepository.findByNombreUsuarioAndContrasena(nombreUsuario, contrasenaUsuario);

        // Verificar que el usuario exista y que la contraseña coincida
        if (usuario != null && usuario.getContrasena().equals(contrasenaUsuario)) {
            return usuario;
        }
        return null;  // Si el usuario no existe o la contraseña no coincide
    }



}
