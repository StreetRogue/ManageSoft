package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.Empresa;
import co.edu.unicauca.managesoft.entities.Estudiante;
import co.edu.unicauca.managesoft.entities.Usuario;
import co.edu.unicauca.managesoft.entities.enumTipoUsuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRepositorioNeonDB implements IUsuarioRepositorio {

    private static final String url = "jdbc:postgresql://ep-twilight-rice-a5meykz5-pooler.us-east-2.aws.neon.tech/neondb?sslmode=require";
    private static final String user = "neondb_owner";
    private static final String password = "npg_J9zkqVtWupl1";

    public static IEmpresaRepositorio repositorioEmpresa;
    public static ICoordinadorRepositorio repositorioCoordinador;
    public static IEstudianteRepositorio repositorioEstudiante;
    public static IProyectoRepositorio repositorioProyecto;

    // Método para obtener la conexión con usuario y contraseña
    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public void setRepositorioEmpresa(IEmpresaRepositorio repositorioEmpresa) {
        this.repositorioEmpresa = repositorioEmpresa;
    }

    @Override
    public void setRepositorioCoordinador(ICoordinadorRepositorio repositorioCoordinador) {
        this.repositorioCoordinador = repositorioCoordinador;
    }

    @Override
    public void setRepositorioEstudiante(IEstudianteRepositorio repositorioEstudiante) {
        this.repositorioEstudiante = repositorioEstudiante;
    }

    @Override
    public void setRepositorioProyecto(IProyectoRepositorio repositorioProyecto) {
        this.repositorioProyecto = repositorioProyecto;
    }

    
    @Override
    public boolean registrarUsuario(Usuario nuevoUsuario) {
        if (existeUsuario(nuevoUsuario.getNombreUsuario())) {
            return false;  // Si el usuario ya existe, no lo registramos
        }

        String sqlUsuario = "INSERT INTO Usuario (nombre_usuario, contrasena, id_rol) "
                + "SELECT ?, ?, r.id FROM Rol r WHERE r.nombre_rol = ?";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sqlUsuario, PreparedStatement.RETURN_GENERATED_KEYS)) {
            // Establecemos los parámetros para la consulta SQL
            stmt.setString(1, nuevoUsuario.getNombreUsuario());
            stmt.setString(2, nuevoUsuario.getContrasenaUsuario());
            stmt.setString(3, nuevoUsuario.getTipoUsuario().toString()); // Guardamos el nombre del enum como String

            // Ejecutamos la consulta de inserción
            int filasAfectadas = stmt.executeUpdate();

            // Si la inserción fue exitosa, obtenemos el ID generado
            if (filasAfectadas > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idUsuario = generatedKeys.getInt(1); // Obtener el ID del usuario insertado

                        // Asignamos el ID al objeto Usuario
                        nuevoUsuario.setIdUsuario(idUsuario);

                        // Registrar en el repositorio adecuado dependiendo del tipo de usuario
                        if (nuevoUsuario instanceof Estudiante) {
                            Estudiante estudiante = (Estudiante) nuevoUsuario;
                            estudiante.setIdUsuario(idUsuario); // Asignar el ID al Estudiante
                            return repositorioEstudiante.guardar(estudiante); // Guardar el Estudiante
                        } else if (nuevoUsuario instanceof Empresa) {
                            Empresa empresa = (Empresa) nuevoUsuario;
                            empresa.setIdUsuario(idUsuario);
                            return repositorioEmpresa.guardar(empresa);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;  // En caso de error o si no se pudo insertar el usuario
    }

    @Override
    public Usuario iniciarSesion(String nombreUsuario, String contrasenaUsuario) {
        // Actualizamos la consulta para hacer un JOIN y obtener el nombre del rol directamente
        String sql = "SELECT u.nombre_usuario, u.contrasena, r.nombre_rol "
                + "FROM Usuario u "
                + "JOIN Rol r ON u.id_rol = r.id "
                + "WHERE u.nombre_usuario = ? AND u.contrasena = ?";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreUsuario);
            stmt.setString(2, contrasenaUsuario);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Convertir el nombre del rol (que es un String) a un valor del enum
                enumTipoUsuario rol = enumTipoUsuario.valueOf(rs.getString("nombre_rol"));

                // Crear el objeto Usuario con los datos obtenidos de la base de datos
                Usuario usuario = new Usuario(
                        rs.getString("nombre_usuario"),
                        rs.getString("contrasena"),
                        rol // Asignar el valor del rol obtenido dinámicamente
                );

                switch (usuario.getTipoUsuario()) {
                    case ESTUDIANTE:
                        return repositorioEstudiante.buscarEstudiante(usuario.getNombreUsuario(), usuario.getContrasenaUsuario());
                    case COORDINADOR:
                        return repositorioCoordinador.buscarCoordinador(usuario.getNombreUsuario(), usuario.getContrasenaUsuario());
                    case EMPRESA:
                        return repositorioEmpresa.buscarEmpresa(usuario.getNombreUsuario(), usuario.getContrasenaUsuario());
                    default:
                        return null; // Tipo de usuario desconocido
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Si no se encuentra el usuario o las credenciales son incorrectas
    }

    private boolean existeUsuario(String nombreUsuario) {
        String sql = "SELECT 1 FROM Usuario WHERE nombre_usuario = ?";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombreUsuario);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Devuelve true si encontró el usuario
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
