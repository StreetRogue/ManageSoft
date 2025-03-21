/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.Empresa;
import co.edu.unicauca.managesoft.entities.Estudiante;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author camac
 */
public class EstudianteRepositorioNeonDB implements IEstudianteRepositorio {

    private static final String url = "jdbc:postgresql://ep-twilight-rice-a5meykz5-pooler.us-east-2.aws.neon.tech/neondb?sslmode=require";
    private static final String user = "neondb_owner";
    private static final String password = "npg_J9zkqVtWupl1";

// Método para obtener la conexión con usuario y contraseña
    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public boolean guardar(Estudiante nuevoEstudiante) {
        if (existeSimca(nuevoEstudiante.getCodigoSimcaEstudiante())) {
            return false;
        }

        String sqlUsuario = "INSERT INTO Usuario (nombre_usuario, contrasena, id_rol) "
                + "SELECT ?, ?, r.id FROM Rol r WHERE r.nombre_rol = ? RETURNING id";
        String sqlEstudiante = "INSERT INTO Estudiante (codigoEstudiante, nombreEstudiante, apellidoEstudiante, correoEstudiante, id_usuario) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = conectar(); PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario, PreparedStatement.RETURN_GENERATED_KEYS); PreparedStatement stmtEstudiante = conn.prepareStatement(sqlEstudiante)) {

            conn.setAutoCommit(false); // Iniciar transacción

            // Insertar usuario
            stmtUsuario.setString(1, nuevoEstudiante.getNombreUsuario());
            stmtUsuario.setString(2, nuevoEstudiante.getContrasenaUsuario());
            stmtUsuario.setString(3, "ESTUDIANTE"); // Suponiendo que el rol se llama 'Estudiante'

            int filasUsuario = stmtUsuario.executeUpdate();
            if (filasUsuario == 0) {
                conn.rollback();
                return false;
            }

            // Obtener el ID del usuario insertado
            int idUsuario;
            try (ResultSet rs = stmtUsuario.getGeneratedKeys()) {
                if (rs.next()) {
                    idUsuario = rs.getInt(1);
                } else {
                    conn.rollback();
                    return false;
                }
            }

            // Insertar estudiante
            stmtEstudiante.setLong(1, Long.parseLong(nuevoEstudiante.getCodigoSimcaEstudiante()));
            stmtEstudiante.setString(2, nuevoEstudiante.getNombreEstudiante());
            stmtEstudiante.setString(3, nuevoEstudiante.getApellidoEstudiante());
            stmtEstudiante.setString(4, nuevoEstudiante.getEmailEstudiante());
            stmtEstudiante.setInt(5, idUsuario);

            if (stmtEstudiante.executeUpdate() > 0) {
                conn.commit();  // Confirmar la transacción si todo salió bien
                return true;
            }

            conn.rollback(); // Si la inserción de estudiante falla, revertir
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Estudiante buscarEstudiante(String nombreUsuario) {
        String sql = "SELECT e.codigoEstudiante, e.nombreEstudiante, e.apellidoEstudiante, e.correoEstudiante, u.nombre_usuario, u.contrasena "
                + "FROM Estudiante e "
                + "INNER JOIN Usuario u ON e.id_usuario = u.id "
                + "WHERE u.nombre_usuario = ?";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombreUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Estudiante(
                        String.valueOf(rs.getLong("codigoEstudiante")),
                        rs.getString("nombreEstudiante"),
                        rs.getString("apellidoEstudiante"),
                        rs.getString("correoEstudiante"),
                        rs.getString("nombre_usuario"),
                        rs.getString("contrasena")
                );
            } else {
                return null; // No se encuentra el estudiante
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Estudiante buscarEstudiante(String nombreUsuario, String contrasenaUsuario) {
        String sql = "SELECT e.codigoEstudiante, e.nombreEstudiante, e.apellidoEstudiante, e.correoEstudiante, u.nombre_usuario, u.contrasena "
                + "FROM Estudiante e "
                + "INNER JOIN Usuario u ON e.id_usuario = u.id "
                + "WHERE u.nombre_usuario = ? AND u.contrasena = ?";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombreUsuario);
            stmt.setString(2, contrasenaUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Estudiante(
                        String.valueOf(rs.getLong("codigoEstudiante")),
                        rs.getString("nombreEstudiante"),
                        rs.getString("apellidoEstudiante"),
                        rs.getString("correoEstudiante"),
                        rs.getString("nombre_usuario"),
                        rs.getString("contrasena")
                );
            } else {
                return null; // No se encuentra el estudiante con ese nombre de usuario y contraseña
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Estudiante> listarEstudiantes() {
        List<Estudiante> listaEstudiantes = new ArrayList<>();
        String sql = "SELECT e.codigoEstudiante, e.nombreEstudiante, e.apellidoEstudiante, e.correoEstudiante, u.nombre_usuario, u.contrasena "
                + "FROM Estudiante e "
                + "JOIN Usuario u ON e.id_usuario = u.id";

        try (Connection conn = conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Estudiante estudiante = new Estudiante(
                        String.valueOf(rs.getLong("codigoEstudiante")),
                        rs.getString("nombreEstudiante"),
                        rs.getString("apellidoEstudiante"),
                        rs.getString("correoEstudiante"),
                        rs.getString("nombre_usuario"),
                        rs.getString("contrasena")
                );
                listaEstudiantes.add(estudiante);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaEstudiantes;
    }

    private boolean existeSimca(String codigo) {
        long aux = Long.parseLong(codigo); // Usamos Long en lugar de Integer
        String sql = "SELECT 1 FROM Estudiante WHERE codigoEstudiante = ?";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, aux); // Establece el parámetro de la consulta con el código recibido
            ResultSet rs = stmt.executeQuery();

            // Si se encuentra un registro, devuelve true
            return rs.next(); // Devuelve true si hay un resultado (si el código existe)
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // En caso de error, devuelve false
        }
    }

}
