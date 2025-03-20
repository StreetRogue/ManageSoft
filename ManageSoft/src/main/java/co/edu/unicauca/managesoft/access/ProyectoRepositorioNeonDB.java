/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.Empresa;
import co.edu.unicauca.managesoft.entities.EstadoEnEjecucion;
import co.edu.unicauca.managesoft.entities.EstadoRechazado;
import co.edu.unicauca.managesoft.entities.EstadoRecibido;
import co.edu.unicauca.managesoft.entities.IEstadoProyecto;
import co.edu.unicauca.managesoft.entities.Proyecto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        String sql = "INSERT INTO Proyecto (nombre, resumen, objetivos, descripcion, tiempo_maximo_meses, presupuesto, estado, nit_empresa) "
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
            stmt.setString(8, empresa.getNitEmpresa()); // Aquí debes pasar el id de la empresa asociada

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
        System.out.println("Buscando proyectos para la empresa con NIT: " + empresa.getNitEmpresa());

        // Modificamos la consulta para incluir el campo 'id'
        String sql = "SELECT id, nombre, resumen, objetivos, descripcion, tiempo_maximo_meses, presupuesto, fecha, estado "
                + "FROM Proyecto WHERE nit_empresa = ?";

        List<Proyecto> proyectos = new ArrayList<>();

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, empresa.getNitEmpresa());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Proyecto proyecto = new Proyecto();

                    // Asignar el id del proyecto
                    proyecto.setIdProyecto(rs.getInt("id"));
                    proyecto.setNombreProyecto(rs.getString("nombre"));
                    proyecto.setResumenProyecto(rs.getString("resumen"));
                    proyecto.setObjetivoProyecto(rs.getString("objetivos"));
                    proyecto.setDescripcionProyecto(rs.getString("descripcion"));
                    proyecto.setMaximoMesesProyecto(String.valueOf(rs.getInt("tiempo_maximo_meses")));
                    proyecto.setPresupuestoProyecto(String.valueOf(rs.getFloat("presupuesto")));
                    proyecto.setFechaPublicacionProyecto(rs.getString("fecha"));

                    // Recuperar el estado del proyecto
                    String estado = rs.getString("estado");
                    IEstadoProyecto estadoProyecto = obtenerEstadoProyecto(estado);
                    proyecto.setEstadoProyecto(estadoProyecto);

                    proyectos.add(proyecto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Aquí imprimimos los proyectos obtenidos, después de que se hayan añadido a la lista
        System.out.println("Proyectos obtenidos de la BD:");
        for (Proyecto p : proyectos) {
            System.out.println("ID: " + p.getIdProyecto() + ", Nombre: " + p.getNombreProyecto() + ", Estado: " + p.getEstadoProyecto());
        }

        return proyectos;
    }

    @Override
    public List<Proyecto> listarProyectosGeneral() {
        System.out.println("Buscando todos los proyectos en la base de datos...");

        String sql = "SELECT p.id, p.nombre AS nombre_proyecto, p.estado, p.presupuesto, "
                + "p.tiempo_maximo_meses, p.resumen, p.objetivos, p.descripcion, p.fecha, "
                + "e.nombre AS nombre_empresa "
                + "FROM Proyecto p "
                + "JOIN Empresa e ON p.nit_empresa = e.nit";
        List<Proyecto> proyectos = new ArrayList<>();

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Proyecto proyecto = new Proyecto();
                proyecto.setIdProyecto(rs.getInt("id"));
                proyecto.setNombreProyecto(rs.getString("nombre_proyecto"));
                proyecto.setResumenProyecto(rs.getString("resumen"));
                proyecto.setObjetivoProyecto(rs.getString("objetivos"));
                proyecto.setDescripcionProyecto(rs.getString("descripcion"));
                proyecto.setMaximoMesesProyecto(String.valueOf(rs.getInt("tiempo_maximo_meses")));
                proyecto.setPresupuestoProyecto(String.valueOf(rs.getFloat("presupuesto")));
                proyecto.setFechaPublicacionProyecto(rs.getString("fecha"));
                proyecto.setNombreEmpresa(rs.getString("nombre_empresa"));

                // Recuperar el estado del proyecto
                String estado = rs.getString("estado");
                IEstadoProyecto estadoProyecto = obtenerEstadoProyecto(estado);
                proyecto.setEstadoProyecto(estadoProyecto);

                proyectos.add(proyecto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Verificar los proyectos obtenidos
        System.out.println("Proyectos obtenidos de la BD:");
        for (Proyecto p : proyectos) {
            System.out.println("Nombre: " + p.getNombreProyecto() + ", Estado: " + p.getEstadoProyecto());
        }

        return proyectos;
    }

    // Método para mapear el estado en String a la clase concreta de estado
    private IEstadoProyecto obtenerEstadoProyecto(String estado) {
        switch (estado) {
            case "RECIBIDO":
                return new EstadoRecibido();
            case "RECHAZADO":
                return new EstadoRechazado();
            case "EN EJECUCIÓN":
                return new EstadoEnEjecucion();
            default:
                throw new IllegalArgumentException("Estado no reconocido: " + estado);
        }
    }

}
