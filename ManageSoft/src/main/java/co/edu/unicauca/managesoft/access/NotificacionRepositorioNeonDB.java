package co.edu.unicauca.managesoft.access;

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
    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public boolean enviarCorreo(Correo correo, Estudiante estudiante, Proyecto proyecto) {
        System.out.println("Código SIMCA del estudiante: " + estudiante.getCodigoSimcaEstudiante());

        String sqlSelectCoordinador = "SELECT id FROM Coordinador WHERE emailcoordinador = ?";
        String sqlInsertSolicitud = "INSERT INTO SolicitudProyecto (codigoEstudiante, id_coordinador, id_proyecto, emailCoordinador, asunto, motivo, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = conectar(); PreparedStatement stmtSelectCoordinador = conn.prepareStatement(sqlSelectCoordinador); PreparedStatement stmtInsertSolicitud = conn.prepareStatement(sqlInsertSolicitud)) {

            conn.setAutoCommit(false);

            // Obtener el ID del coordinador
            stmtSelectCoordinador.setString(1, correo.getDestinatario());

            try (ResultSet rs = stmtSelectCoordinador.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("❌ No se encontró el coordinador con el email: " + correo.getDestinatario());
                    conn.rollback();
                    return false;
                }

                int idCoordinador = rs.getInt("id");

                // Insertar la solicitud en la tabla SolicitudProyecto
                stmtInsertSolicitud.setLong(1, Long.parseLong(estudiante.getNombreEstudiante())); // Código del estudiante
                stmtInsertSolicitud.setInt(2, idCoordinador); // ID del coordinador
                stmtInsertSolicitud.setInt(3, proyecto.getIdProyecto()); // ID del proyecto
                stmtInsertSolicitud.setString(4, correo.getDestinatario()); // Email del coordinador
                stmtInsertSolicitud.setString(5, correo.getAsunto()); // Asunto del correo
                stmtInsertSolicitud.setString(6, correo.getMensaje()); // Motivo del contacto
                stmtInsertSolicitud.setString(7, "ENVIADO"); // Estado inicial

                if (stmtInsertSolicitud.executeUpdate() > 0) {
                    conn.commit();
                    System.out.println("✅ Solicitud de contacto registrada correctamente.");
                    return true;
                }
            }
            conn.rollback();
        } catch (SQLException e) {
            System.err.println("❌ Error al enviar la solicitud: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

}
