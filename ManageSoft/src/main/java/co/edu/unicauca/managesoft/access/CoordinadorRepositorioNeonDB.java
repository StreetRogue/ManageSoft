/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.Coordinador;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author camac
 */
public class CoordinadorRepositorioNeonDB implements ICoordinadorRepositorio {

    private static final String url = "jdbc:postgresql://ep-twilight-rice-a5meykz5-pooler.us-east-2.aws.neon.tech/neondb?sslmode=require";
    private static final String user = "neondb_owner";
    private static final String password = "npg_J9zkqVtWupl1";

// Método para obtener la conexión con usuario y contraseña
    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public Coordinador buscarCoordinador(String nombreUsuario) {
        String sql = "SELECT u.nombre_usuario, u.contrasena, u.id_rol, "
                + "c.nombre, c.email, c.telefono "
                + "FROM Coordinador c "
                + "INNER JOIN Usuario u ON c.id_usuario = u.id "
                + "WHERE u.nombre_usuario = ?";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombreUsuario);  // Establecer el parámetro nombre_usuario
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Coordinador(
                        rs.getString("nombre_usuario"),
                        rs.getString("contrasena"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("telefono")
                );
            } else {
                return null;  // No se encuentra el coordinador
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Coordinador buscarCoordinador(String nombreUsuario, String contrasenaUsuario) {
        String sql = "SELECT u.nombre_usuario, u.contrasena, u.id_rol, "
                + "c.nombre, c.email, c.telefono "
                + "FROM Coordinador c "
                + "INNER JOIN Usuario u ON c.id_usuario = u.id "
                + "WHERE u.nombre_usuario = ? AND u.contrasena = ?";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombreUsuario);  // Establecer el parámetro nombre_usuario
            stmt.setString(2, contrasenaUsuario);  // Establecer el parámetro contrasena
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Coordinador(
                        rs.getString("nombre_usuario"),
                        rs.getString("contrasena"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("telefono")
                );
            } else {
                return null;  // No se encuentra el coordinador con ese usuario y contraseña
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
