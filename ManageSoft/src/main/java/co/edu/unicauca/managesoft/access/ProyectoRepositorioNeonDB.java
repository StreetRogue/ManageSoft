/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.Empresa;
import co.edu.unicauca.managesoft.entities.Proyecto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProyectoRepositorioNeonDB implements IProyectoRepositorio {

    private static final String url = "jdbc:postgresql://ep-twilight-rice-a5meykz5-pooler.us-east-2.aws.neon.tech/neondb?sslmode=require";
    private static final String user = "neondb_owner";
    private static final String password = "npg_J9zkqVtWupl1";

    // Método para obtener la conexión con usuario y contraseña
    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public boolean guardarProyecto(Proyecto nuevoProyecto, Empresa empresa) {
        String sql = "INSERT INTO Proyecto (nombre, resumen, objetivos, descripcion, tiempo_maximo_meses, presupuesto, estado, id_empresa) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            stmt.setString(1, nuevoProyecto.getNombreProyecto());
            stmt.setString(2, nuevoProyecto.getResumenProyecto());
            stmt.setString(3, nuevoProyecto.getObjetivoProyecto());
            stmt.setString(4, nuevoProyecto.getDescripcionProyecto());
            stmt.setInt(5, Integer.parseInt(nuevoProyecto.getMaximoMesesProyecto()));
            stmt.setFloat(6, Float.parseFloat((nuevoProyecto.getPresupuestoProyecto())));
            stmt.setString(7, nuevoProyecto.getEstadoProyecto().obtenerEstado()); // Por ejemplo, 'RECIBIDO' si no se ha establecido otro
            stmt.setInt(8, empresa.getIdUsuario()); // Aquí debes pasar el id de la empresa asociada

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Proyecto> listarProyectos(Empresa empresa) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    


}
