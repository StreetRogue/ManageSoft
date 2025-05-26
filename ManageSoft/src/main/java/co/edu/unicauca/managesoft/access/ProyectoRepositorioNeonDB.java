/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.Empresa;
import co.edu.unicauca.managesoft.entities.EstadoAceptado;
import co.edu.unicauca.managesoft.entities.EstadoCerrado;
import co.edu.unicauca.managesoft.entities.EstadoEnEjecucion;
import co.edu.unicauca.managesoft.entities.EstadoRechazado;
import co.edu.unicauca.managesoft.entities.EstadoRecibido;
import co.edu.unicauca.managesoft.entities.IEstadoProyecto;
import co.edu.unicauca.managesoft.infra.ProyectTable;
import co.edu.unicauca.managesoft.entities.Proyecto;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

public class ProyectoRepositorioNeonDB implements IProyectoRepositorio {

    private static final String url = "jdbc:postgresql://ep-twilight-rice-a5meykz5-pooler.us-east-2.aws.neon.tech/neondb?sslmode=require";
    private static final String user = "neondb_owner";
    private static final String password = "npg_J9zkqVtWupl1";

    // Método para obtener la conexión con usuario y contraseña
    protected Connection conectar() throws SQLException {
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
            stmt.setString(7, nuevoProyecto.getEstadoProyecto().obtenerEstado());
            stmt.setString(8, empresa.getNitEmpresa());

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
    public List<Proyecto> listarProyectos(String nitEmpresa) {
        System.out.println("Buscando proyectos para la empresa con NIT: " + nitEmpresa);

        String sql = "SELECT id, nombre, resumen, objetivos, descripcion, tiempo_maximo_meses, presupuesto, fecha, estado "
                + "FROM Proyecto WHERE nit_empresa = ? ORDER BY nombre ASC;";

        List<Proyecto> proyectos = new ArrayList<>();

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nitEmpresa);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Proyecto proyecto = new Proyecto();

                    // Asignar datos básicos
                    proyecto.setIdProyecto(rs.getInt("id"));
                    proyecto.setNombreProyecto(rs.getString("nombre"));
                    proyecto.setResumenProyecto(rs.getString("resumen"));
                    proyecto.setObjetivoProyecto(rs.getString("objetivos"));
                    proyecto.setDescripcionProyecto(rs.getString("descripcion"));
                    proyecto.setMaximoMesesProyecto(String.valueOf(rs.getInt("tiempo_maximo_meses")));
                    proyecto.setPresupuestoProyecto(String.valueOf(rs.getFloat("presupuesto")));
                    proyecto.setFechaPublicacionProyecto(rs.getString("fecha"));

                    // Recuperar y asignar estado actual
                    String estado = rs.getString("estado");
                    IEstadoProyecto estadoProyecto = obtenerEstadoProyecto(estado);
                    proyecto.setEstadoProyecto(estadoProyecto);

                    // Cargar historial de estados (Mementos) para este proyecto
                    cargarHistorialProyecto(proyecto, conn);

                    proyectos.add(proyecto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Proyectos obtenidos de la BD:");
        for (Proyecto p : proyectos) {
            System.out.println("ID: " + p.getIdProyecto() + ", Nombre: " + p.getNombreProyecto() + ", Estado: " + p.getEstadoProyecto());
        }

        return proyectos;
    }

    @Override
    public List<ProyectTable> listarProyectosGeneral() {
        System.out.println("Buscando todos los proyectos en la base de datos...");

        String sql = "SELECT p.id, "
                + "p.nombre AS nombre_proyecto, "
                + "p.estado, "
                + "p.presupuesto, "
                + "p.tiempo_maximo_meses, "
                + "p.resumen, "
                + "p.objetivos, "
                + "p.descripcion, "
                + "p.fecha, "
                + "e.nit AS nit_empresa, "
                + "e.nombre AS nombre_empresa, "
                + "e.sector, "
                + "e.email, "
                + "e.telefono_contacto, "
                + "e.nombre_contacto, "
                + "e.apellido_contacto, "
                + "e.cargo_contacto, "
                + "CASE WHEN sp.estado = 'ENVIADO' THEN TRUE ELSE FALSE END AS correo_enviado "
                + "FROM Proyecto p "
                + "JOIN Empresa e ON p.nit_empresa = e.nit "
                + "LEFT JOIN SolicitudProyecto sp ON p.id = sp.id_proyecto "
                + "ORDER BY p.nombre ASC;";

        List<ProyectTable> proyectos = new ArrayList<>();

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ProyectTable proyecto = new ProyectTable();
                proyecto.setIdProyecto(rs.getInt("id"));
                proyecto.setNombreProyecto(rs.getString("nombre_proyecto"));
                proyecto.setResumenProyecto(rs.getString("resumen"));
                proyecto.setObjetivoProyecto(rs.getString("objetivos"));
                proyecto.setDescripcionProyecto(rs.getString("descripcion"));
                proyecto.setMaximoMesesProyecto(String.valueOf(rs.getInt("tiempo_maximo_meses")));
                proyecto.setPresupuestoProyecto(String.valueOf(rs.getFloat("presupuesto")));
                proyecto.setFechaPublicacionProyecto(rs.getString("fecha"));
                proyecto.setNombreEmpresa(rs.getString("nombre_empresa"));
                proyecto.setCorreoEnviado(rs.getBoolean("correo_enviado"));

                // Crear y setear objeto Empresa
                Empresa empresa = new Empresa();
                empresa.setNitEmpresa(rs.getString("nit_empresa"));
                empresa.setNombreEmpresa(rs.getString("nombre_empresa"));
                empresa.setSectorEmpresa(rs.getString("sector"));
                empresa.setEmailEmpresa(rs.getString("email"));
                empresa.setContactoEmpresa(rs.getString("telefono_contacto"));
                empresa.setNombreContactoEmpresa(rs.getString("nombre_contacto"));
                empresa.setApellidoContactoEmpresa(rs.getString("apellido_contacto"));
                empresa.setCargoContactoEmpresa(rs.getString("cargo_contacto"));

                proyecto.setEmpresa(empresa);

                // Estado del proyecto
                String estado = rs.getString("estado");
                IEstadoProyecto estadoProyecto = obtenerEstadoProyecto(estado);
                proyecto.setEstadoProyecto(estadoProyecto);

                proyectos.add(proyecto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Proyectos obtenidos de la BD:");
        for (ProyectTable p : proyectos) {
            System.out.println("Nombre: " + p.getNombreProyecto() + ", Empresa: " + (p.getEmpresa() != null ? p.getEmpresa().getNombreEmpresa() : "N/A"));
        }

        return proyectos;
    }

    // Método para mapear el estado en String a la clase concreta de estado
    @Override
    public IEstadoProyecto obtenerEstadoProyecto(String estado) {
        switch (estado) {
            case "RECIBIDO":
                return new EstadoRecibido();
            case "RECHAZADO":
                return new EstadoRechazado();
            case "EN_EJECUCION":
                return new EstadoEnEjecucion();
            case "CERRADO":
                return new EstadoCerrado();
            case "ACEPTADO":
                return new EstadoAceptado();
            default:
                throw new IllegalArgumentException("Estado no reconocido: " + estado);
        }
    }

    @Override
    public boolean actualizarEstadoProyecto(Proyecto proyecto, String nuevoEstado) {
        String sqlUpdate = "UPDATE Proyecto SET estado = ? WHERE id = ?";
        String sqlInsertHistorial = "INSERT INTO HistorialProyecto (idProyecto, estado) VALUES (?, ?)";

        try (
                Connection conn = conectar(); PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate); PreparedStatement stmtHistorial = conn.prepareStatement(sqlInsertHistorial)) {
            conn.setAutoCommit(false);

            stmtUpdate.setString(1, nuevoEstado);
            stmtUpdate.setInt(2, proyecto.getIdProyecto());

            int filasAfectadas = stmtUpdate.executeUpdate();

            if (filasAfectadas > 0) {
                stmtHistorial.setInt(1, proyecto.getIdProyecto());
                stmtHistorial.setString(2, nuevoEstado);

                stmtHistorial.executeUpdate();
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
    public List<ProyectTable> listarProyectosGeneralEstudiantes() {
        System.out.println("Buscando proyectos disponibles para estudiantes...");

        String sql = "SELECT p.id, "
                + "p.nombre AS nombre_proyecto, "
                + "p.estado, "
                + "p.presupuesto, "
                + "p.tiempo_maximo_meses, "
                + "p.resumen, "
                + "p.objetivos, "
                + "p.descripcion, "
                + "p.fecha, "
                + "e.nit AS nit_empresa, "
                + "e.nombre AS nombre_empresa, "
                + "e.sector, "
                + "e.email, "
                + "e.telefono_contacto, "
                + "e.nombre_contacto, "
                + "e.apellido_contacto, "
                + "e.cargo_contacto "
                + "FROM Proyecto p "
                + "JOIN Empresa e ON p.nit_empresa = e.nit "
                + "WHERE p.estado = 'ACEPTADO' "
                + "ORDER BY p.nombre ASC";

        List<ProyectTable> proyectos = new ArrayList<>();

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ProyectTable proyecto = new ProyectTable();
                proyecto.setIdProyecto(rs.getInt("id"));
                proyecto.setNombreProyecto(rs.getString("nombre_proyecto"));
                proyecto.setResumenProyecto(rs.getString("resumen"));
                proyecto.setObjetivoProyecto(rs.getString("objetivos"));
                proyecto.setDescripcionProyecto(rs.getString("descripcion"));
                proyecto.setMaximoMesesProyecto(String.valueOf(rs.getInt("tiempo_maximo_meses")));
                proyecto.setPresupuestoProyecto(String.valueOf(rs.getFloat("presupuesto")));
                proyecto.setFechaPublicacionProyecto(rs.getString("fecha"));
                proyecto.setNombreEmpresa(rs.getString("nombre_empresa"));

                // Crear y setear objeto Empresa
                Empresa empresa = new Empresa();
                empresa.setNitEmpresa(rs.getString("nit_empresa"));
                empresa.setNombreEmpresa(rs.getString("nombre_empresa"));
                empresa.setSectorEmpresa(rs.getString("sector"));
                empresa.setEmailEmpresa(rs.getString("email"));
                empresa.setContactoEmpresa(rs.getString("telefono_contacto"));
                empresa.setNombreContactoEmpresa(rs.getString("nombre_contacto"));
                empresa.setApellidoContactoEmpresa(rs.getString("apellido_contacto"));
                empresa.setCargoContactoEmpresa(rs.getString("cargo_contacto"));

                proyecto.setEmpresa(empresa);

                // Estado del proyecto
                String estado = rs.getString("estado");
                IEstadoProyecto estadoProyecto = obtenerEstadoProyecto(estado);
                proyecto.setEstadoProyecto(estadoProyecto);

                proyectos.add(proyecto);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar proyectos disponibles para estudiantes: " + e.getMessage());
        }

        return proyectos;
    }

    @Override
    public Proyecto encontrarPorId(String idProyectoStr) {
        Proyecto proyecto = new Proyecto();
        int idProyecto = Integer.parseInt(idProyectoStr);

        // Consulta modificada para incluir datos de la empresa
        String sql = "SELECT p.id, p.nombre, p.resumen, p.objetivos, p.descripcion, "
                + "p.tiempo_maximo_meses, p.presupuesto, p.fecha, p.estado, p.nit_empresa, "
                + "e.nombre AS nombre_empresa, e.sector, e.email, e.telefono_contacto, "
                + "e.nombre_contacto, e.apellido_contacto, e.cargo_contacto "
                + "FROM Proyecto p "
                + "LEFT JOIN Empresa e ON p.nit_empresa = e.nit "
                + "WHERE p.id = ?";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProyecto);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Datos básicos del proyecto
                    proyecto.setIdProyecto(rs.getInt("id"));
                    proyecto.setNombreProyecto(rs.getString("nombre"));
                    proyecto.setResumenProyecto(rs.getString("resumen"));
                    proyecto.setObjetivoProyecto(rs.getString("objetivos"));
                    proyecto.setDescripcionProyecto(rs.getString("descripcion"));
                    proyecto.setMaximoMesesProyecto(String.valueOf(rs.getInt("tiempo_maximo_meses")));
                    proyecto.setPresupuestoProyecto(String.valueOf(rs.getFloat("presupuesto")));
                    proyecto.setFechaPublicacionProyecto(rs.getString("fecha"));

                    // Estado del proyecto
                    String estadoStr = rs.getString("estado");
                    IEstadoProyecto estadoProyecto = obtenerEstadoProyecto(estadoStr);
                    proyecto.setEstadoProyecto(estadoProyecto);

                    // Datos de la empresa (si existe relación)
                    String nitEmpresa = rs.getString("nit_empresa");
                    if (nitEmpresa != null) {
                        Empresa empresa = new Empresa();
                        empresa.setNitEmpresa(nitEmpresa);
                        empresa.setNombreEmpresa(rs.getString("nombre_empresa"));
                        empresa.setSectorEmpresa(rs.getString("sector"));
                        empresa.setEmailEmpresa(rs.getString("email"));
                        empresa.setContactoEmpresa(rs.getString("telefono_contacto"));
                        empresa.setNombreContactoEmpresa(rs.getString("nombre_contacto"));
                        empresa.setApellidoContactoEmpresa(rs.getString("apellido_contacto"));
                        empresa.setCargoContactoEmpresa(rs.getString("cargo_contacto"));

                        proyecto.setEmpresa(empresa);
                    }
                } else {
                    return null;
                }
            }

            // Cargar historial de mementos
            cargarHistorialProyecto(proyecto, conn);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return proyecto;
    }

    @Override
    public int cantProyectoporEstado(String estado, String periodoAcademico) {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM Proyecto WHERE estado = ? AND fecha BETWEEN ? AND ?";

        // Convertir "2025-1" a rango de fechas (inicio y fin)
        String[] partes = periodoAcademico.split("-");
        int anio = Integer.parseInt(partes[0]);
        int semestre = Integer.parseInt(partes[1]);

        LocalDate inicio;
        LocalDate fin;

        if (semestre == 1) {
            inicio = LocalDate.of(anio, 2, 1);
            fin = LocalDate.of(anio, 6, 30);
        } else {
            inicio = LocalDate.of(anio, 7, 1);
            fin = LocalDate.of(anio, 11, 20);
        }

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estado);
            stmt.setDate(2, java.sql.Date.valueOf(inicio));
            stmt.setDate(3, java.sql.Date.valueOf(fin));

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    @Override
    public int cantProyectosEvaluados(String periodoAcademico) {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM Proyecto WHERE estado = 'ACEPTADO' OR estado = 'RECHAZADO'";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    @Override
    public int cantTasaAceptacion(String periodoAcademico) {
        int aceptados = 0;
        int rechazados = 0;
        int tasa = 0;

        String sqlAceptados = "SELECT COUNT(*) FROM Proyecto WHERE estado = 'ACEPTADO'";
        String sqlRechazados = "SELECT COUNT(*) FROM Proyecto WHERE estado = 'RECHAZADO'";

        try (Connection conn = conectar(); PreparedStatement stmtAceptados = conn.prepareStatement(sqlAceptados); PreparedStatement stmtRechazados = conn.prepareStatement(sqlRechazados); ResultSet rsAceptados = stmtAceptados.executeQuery(); ResultSet rsRechazados = stmtRechazados.executeQuery()) {

            if (rsAceptados.next()) {
                aceptados = rsAceptados.getInt(1);
            }

            if (rsRechazados.next()) {
                rechazados = rsRechazados.getInt(1);
            }

            int total = aceptados + rechazados;
            if (total > 0) {
                tasa = (aceptados * 100) / total; // División entera sin decimales
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasa;
    }

    @Override
    public int avgProyectoDiasEnAceptar() {
        String sql = "SELECT AVG(EXTRACT(DAY FROM ("
                + "SELECT hp.fechaCambio FROM HistorialProyecto hp "
                + "WHERE hp.idProyecto = p.id AND hp.estado = 'ACEPTADO' "
                + "ORDER BY hp.fechaCambio ASC LIMIT 1"
                + ") - p.fecha::timestamp))::integer "
                + "FROM Proyecto p "
                + "WHERE p.id IN ("
                + "SELECT DISTINCT idProyecto FROM HistorialProyecto "
                + "WHERE estado = 'ACEPTADO')";

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = ((java.sql.Statement) stmt).executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


    //Memento
    private void cargarHistorialProyecto(Proyecto proyecto, Connection conn) {
        String sql = "SELECT estado FROM HistorialProyecto WHERE idProyecto = ? ORDER BY idProyecto ASC"; // Asumiendo que 'id' es autoincremental y mantiene el orden

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, proyecto.getIdProyecto());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String estadoStr = rs.getString("estado");
                    IEstadoProyecto estado = obtenerEstadoProyecto(estadoStr);
                    proyecto.getCaretaker().addMemento(proyecto.saveToMemento(estado));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
