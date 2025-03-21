package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.Coordinador;
import co.edu.unicauca.managesoft.entities.Correo;
import co.edu.unicauca.managesoft.entities.Estudiante;
import co.edu.unicauca.managesoft.entities.Proyecto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificacionRepositorioNeonDB implements INotificacionRepositorio {

    private static final String url = "jdbc:postgresql://ep-twilight-rice-a5meykz5-pooler.us-east-2.aws.neon.tech/neondb?sslmode=require";
    private static final String user = "neondb_owner";
    private static final String password = "npg_J9zkqVtWupl1";

// Método para obtener la conexión con usuario y contraseña
    protected Connection conectar() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public boolean enviarCorreo(Correo correo, Estudiante estudiante, Proyecto proyecto) {
        System.out.println(estudiante.getCodigoSimcaEstudiante());
        String sqlSelectCoordinador = "SELECT id FROM Coordinador WHERE emailcoordinador = ?";

        String sqlInsertSolicitud = "INSERT INTO SolicitudProyecto (codigoEstudiante, id_coordinador, id_proyecto, emailCoordinador, asunto, motivo, estado)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = conectar(); PreparedStatement stmtSelectCoordinador = conn.prepareStatement(sqlSelectCoordinador); PreparedStatement stmtInsertSolicitud = conn.prepareStatement(sqlInsertSolicitud)) {

            conn.setAutoCommit(false);

            // Obtener el ID del coordinador
            stmtSelectCoordinador.setString(1, correo.getDestinatario());

            try (ResultSet rs = stmtSelectCoordinador.executeQuery()) {
                if (!rs.next()) {

                    System.out.println("No se encontró el coordinador con el email: " + correo.getDestinatario());

                    conn.rollback();
                    return false;
                }

                int idCoordinador = rs.getInt("id");

                // Insertar la solicitud en la tabla SolicitudProyecto
                stmtInsertSolicitud.setLong(1, Long.parseLong(estudiante.getCodigoSimcaEstudiante())); // Usar el código del estudiante
                stmtInsertSolicitud.setInt(2, idCoordinador);
                stmtInsertSolicitud.setInt(3, proyecto.getIdProyecto());
                stmtInsertSolicitud.setString(4, correo.getDestinatario());
                stmtInsertSolicitud.setString(5, correo.getAsunto());
                stmtInsertSolicitud.setString(6, correo.getMensaje());
                stmtInsertSolicitud.setString(7, "ENVIADO");

                if (stmtInsertSolicitud.executeUpdate() > 0) {
                    conn.commit();
                    System.out.println("Solicitud de contacto registrada correctamente.");
                    return true;
                }
            }
            conn.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean enviarComentario(String comentario, Coordinador coordinador, Proyecto proyecto) {
        String emailCoordinador = coordinador.getEmail();
        String emailEmpresa = obtenerEmailEmpresa(proyecto.getNombreEmpresa()); // Obtener el email de la empresa desde el nombre de la empresa

        if (emailEmpresa == null) {
            // Si no se encontró el correo de la empresa, no se puede enviar el comentario
            System.out.println("No se encontró el correo de la empresa.");
            return false;
        }

        // SQL para insertar el comentario en la base de datos
        String sql = "INSERT INTO Comentario (comentario, email_coordinador, email_empresa, id_proyecto) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conectar().prepareStatement(sql)) {
            stmt.setString(1, comentario);
            stmt.setString(2, emailCoordinador);
            stmt.setString(3, emailEmpresa);
            stmt.setInt(4, proyecto.getIdProyecto()); // Obtener el ID del proyecto

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Retorna true si se insertó el comentario correctamente
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener el correo de la empresa a partir del nombre de la empresa
    private String obtenerEmailEmpresa(String nombreEmpresa) {
        String emailEmpresa = null;
        String sql = "SELECT email FROM Empresa WHERE nombre = ?";

        try (PreparedStatement stmt = conectar().prepareStatement(sql)) {
            stmt.setString(1, nombreEmpresa);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    emailEmpresa = rs.getString("email");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emailEmpresa;
    }

}
