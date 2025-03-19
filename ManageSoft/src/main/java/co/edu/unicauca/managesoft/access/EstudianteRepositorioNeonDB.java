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

        // Validamos que el código de estudiante no exista en la base de datos
        if (existeSimca(nuevoEstudiante.getCodigoSimcaEstudiante())) {
            return false; // No guardar si el código de estudiante ya existe
        }

        // Iniciamos una transacción manualmente para asegurar consistencia
        Connection conn = null;
        PreparedStatement stmtEstudiante = null;

        try {
            conn = conectar();
            conn.setAutoCommit(false); // Desactivar auto-commit para asegurar consistencia

            // Insertar en la tabla estudiantes, sin necesidad de insertar el usuario
            String sqlEstudiante = "INSERT INTO Estudiante (codigo_estudiante, id_usuario, nombre, apellido, correo) "
                    + "VALUES (?, ?, ?, ?, ?)";

            stmtEstudiante = conn.prepareStatement(sqlEstudiante);

            int codigoSimcaEntero = Integer.parseInt(nuevoEstudiante.getCodigoSimcaEstudiante());

            stmtEstudiante.setInt(1, codigoSimcaEntero);
            stmtEstudiante.setInt(2, nuevoEstudiante.getIdUsuario()); // Asumimos que el id_usuario ya está establecido
            stmtEstudiante.setString(3, nuevoEstudiante.getNombreEstudiante());
            stmtEstudiante.setString(4, nuevoEstudiante.getApellidoEstudiante());
            stmtEstudiante.setString(5, nuevoEstudiante.getEmailEstudiante());

            int filasAfectadas = stmtEstudiante.executeUpdate();

            if (filasAfectadas > 0) {
                conn.commit(); // Confirmar la transacción si se inserta correctamente
                return true;
            } else {
                conn.rollback(); // Si la inserción falla, revertir la transacción
                return false;
            }

        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback(); // Revertir cambios en caso de error
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmtEstudiante != null) {
                    stmtEstudiante.close();
                }
                if (conn != null) {
                    conn.setAutoCommit(true); // Restaurar auto-commit
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Estudiante buscarEstudiante(String nombreUsuario) {
        String sql = "SELECT e.codigo_estudiante, e.nombre, e.apellido, e.correo, u.nombre_usuario, u.contrasena "
                + "FROM Estudiante e "
                + "INNER JOIN Usuario u ON e.id_usuario = u.id "
                + "WHERE u.nombre_usuario = ?";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombreUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Estudiante(
                        String.valueOf(rs.getInt("codigo_estudiante")),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo"),
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
        String sql = "SELECT e.codigo_estudiante, e.nombre, e.apellido, e.correo, u.nombre_usuario, u.contrasena "
                + "FROM Estudiante e "
                + "INNER JOIN Usuario u ON e.id_usuario = u.id "
                + "WHERE u.nombre_usuario = ? AND u.contrasena = ?";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombreUsuario);
            stmt.setString(2, contrasenaUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Estudiante(
                         String.valueOf(rs.getInt("codigo_estudiante")),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo"),
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
        String sql = "SELECT e.codigo_estudiante, e.nombre, e.apellido, e.correo, u.nombre_usuario, u.contrasena "
                + "FROM Estudiante e "
                + "JOIN Usuario u ON e.id_usuario = u.id";

        try (Connection conn = conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Estudiante estudiante = new Estudiante(
                        String.valueOf(rs.getInt("codigo_estudiante")),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo"),
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
        int aux = Integer.parseInt(codigo);
        String sql = "SELECT 1 FROM Estudiante WHERE codigo_estudiante = ?";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, aux); // Establece el parámetro de la consulta con el código recibido
            ResultSet rs = stmt.executeQuery();

            // Si se encuentra un registro, devuelve true
            return rs.next(); // Devuelve true si hay un resultado (si el código existe)
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // En caso de error, devuelve false
        }
    }

}
