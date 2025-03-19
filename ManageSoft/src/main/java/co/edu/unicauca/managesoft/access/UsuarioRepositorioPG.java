package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.Usuario;
import co.edu.unicauca.managesoft.entities.enumTipoUsuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRepositorioPG implements IUsuarioRepositorio {

    // private static final String URL = "jdbc:postgresql://localhost:5432/BaseSoftware2"; // Conexion Lmao
    private static final String url = "jdbc:postgresql://26.218.42.255:5432/BaseSoftware2"; // Conexion Everybody
    private static final String user = "postgres";
    private static final String password = "postgres";

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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    

    @Override
    public boolean registrarUsuario(Usuario nuevoUsuario) {
        // Consulta para insertar un usuario, asegurando que el id_rol sea obtenido de la tabla roles
        String sql = "INSERT INTO Usuario (nombre_usuario, contrasena, id_rol) "
                + "SELECT ?, ?, r.id FROM Rol r WHERE r.nombre_rol = ?";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nuevoUsuario.getNombreUsuario());
            stmt.setString(2, nuevoUsuario.getContrasenaUsuario());
            stmt.setString(3, nuevoUsuario.getTipoUsuario().toString()); // Guardamos el nombre del enum como String

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
                return new Usuario(
                        rs.getString("nombre_usuario"),
                        rs.getString("contrasena"),
                        rol // Asignar el valor del rol obtenido dinámicamente
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Si no se encuentra el usuario o las credenciales son incorrectas
    }

    private boolean existeUsuario(String nombreUsuario) {
        String sql = "SELECT 1 FROM usuarios WHERE nombre_usuario = ?";
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
