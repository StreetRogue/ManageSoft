package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.Empresa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmpresaRepositorioPG implements IEmpresaRepositorio {

    // private static final String URL = "jdbc:postgresql://localhost:5432/BaseSoftware2"; // Conexion Lmao
    private static final String url = "jdbc:postgresql://26.218.42.255:5432/BaseSoftware2"; // Conexion Everybody
    private static final String user = "postgres";
    private static final String password = "postgres";

// Método para obtener la conexión con usuario y contraseña
    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public Empresa buscarEmpresa(String nombreUsuario) {
        String sql = "SELECT e.nit, e.nombre, e.email, e.sector, e.telefono, "
                + "e.representante_nombre, e.representante_apellido, e.cargo, "
                + "u.nombre_usuario, u.contrasena "
                + "FROM Empresa e "
                + "INNER JOIN Usuario u ON e.id_usuario = u.id "
                + "WHERE u.nombre_usuario = ?";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombreUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Empresa(
                        rs.getString("nit"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("sector"),
                        rs.getString("telefono"),
                        rs.getString("representante_nombre"),
                        rs.getString("representante_apellido"),
                        rs.getString("cargo"),
                        rs.getString("nombre_usuario"),
                        rs.getString("contrasena")
                );
            } else {
                return null; // No se encuentra la empresa
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Empresa buscarEmpresa(String nombreUsuario, String contrasenaUsuario) {
        String sql = "SELECT e.nit, e.nombre, e.email, e.sector, e.telefono, "
                + "e.representante_nombre, e.representante_apellido, e.cargo, "
                + "u.nombre_usuario, u.contrasena "
                + "FROM Empresa e "
                + "INNER JOIN Usuario u ON e.id_usuario = u.id "
                + "WHERE u.nombre_usuario = ? AND u.contrasena = ?";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombreUsuario);
            stmt.setString(2, contrasenaUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Empresa(
                        rs.getString("nit"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("sector"),
                        rs.getString("telefono"),
                        rs.getString("representante_nombre"),
                        rs.getString("representante_apellido"),
                        rs.getString("cargo"),
                        rs.getString("nombre_usuario"),
                        rs.getString("contrasena")
                );
            } else {
                return null; // No se encuentra la empresa con ese usuario y contraseña
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean guardar(Empresa nuevaEmpresa) {
        if (existeNit(nuevaEmpresa.getNitEmpresa())) {
            return false; // No guardar si el NIT ya existe
        }

        // Iniciamos una transacción manualmente para insertar primero el usuario y luego la empresa
        Connection conn = null;
        PreparedStatement stmtUsuario = null;
        PreparedStatement stmtEmpresa = null;

        try {
            conn = conectar();
            conn.setAutoCommit(false); // Desactivar auto-commit para asegurar consistencia

            // Insertar en usuarios asegurando que el rol sea 'EMPRESA'
            String sqlUsuario = "INSERT INTO usuarios (nombre_usuario, contrasena, id_rol) "
                    + "SELECT ?, ?, id FROM roles WHERE nombre_rol = 'EMPRESA' RETURNING id";
            stmtUsuario = conn.prepareStatement(sqlUsuario);
            stmtUsuario.setString(1, nuevaEmpresa.getNombreUsuario());
            stmtUsuario.setString(2, nuevaEmpresa.getContrasenaUsuario());

            ResultSet rs = stmtUsuario.executeQuery();
            int usuarioId = -1;
            if (rs.next()) {
                usuarioId = rs.getInt("id");
            } else {
                conn.rollback(); // Si no se encontró el rol, cancelar la transacción
                return false;
            }

            // Insertar en la tabla empresas con el ID del usuario recién creado
            String sqlEmpresa = "INSERT INTO empresas (nit, nombre, email, sector, telefono, representante_nombre, representante_apellido, cargo, usuario_id) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmtEmpresa = conn.prepareStatement(sqlEmpresa);
            stmtEmpresa.setString(1, nuevaEmpresa.getNitEmpresa());
            stmtEmpresa.setString(2, nuevaEmpresa.getNombreEmpresa());
            stmtEmpresa.setString(3, nuevaEmpresa.getEmailEmpresa());
            stmtEmpresa.setString(4, nuevaEmpresa.getSectorEmpresa());
            stmtEmpresa.setString(5, nuevaEmpresa.getContactoEmpresa());
            stmtEmpresa.setString(6, nuevaEmpresa.getNombreContactoEmpresa());
            stmtEmpresa.setString(7, nuevaEmpresa.getApellidoContactoEmpresa());
            stmtEmpresa.setString(8, nuevaEmpresa.getCargoContactoEmpresa());
            stmtEmpresa.setInt(9, usuarioId);

            int filasAfectadas = stmtEmpresa.executeUpdate();

            if (filasAfectadas > 0) {
                conn.commit(); // Confirmar la transacción
                return true;
            } else {
                conn.rollback(); // Si la inserción en empresas falla, revertir la transacción
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
                if (stmtUsuario != null) {
                    stmtUsuario.close();
                }
                if (stmtEmpresa != null) {
                    stmtEmpresa.close();
                }
                if (conn != null) {
                    conn.setAutoCommit(true); // Restaurar auto-commit
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Empresa> listarEmpresas() {
        List<Empresa> listaEmpresas = new ArrayList<>();
        String sql = "SELECT e.nit, e.nombre, e.email, e.sector, e.telefono, "
                + "e.representante_nombre, e.representante_apellido, e.cargo, "
                + "u.nombre_usuario, u.contrasena "
                + "FROM empresas e "
                + "JOIN usuarios u ON e.usuario_id = u.id";

        try (Connection conn = conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Empresa empresa = new Empresa(
                        rs.getString("nit"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("sector"),
                        rs.getString("telefono"),
                        rs.getString("representante_nombre"),
                        rs.getString("representante_apellido"),
                        rs.getString("cargo"),
                        rs.getString("nombre_usuario"),
                        rs.getString("contrasena")
                );
                listaEmpresas.add(empresa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaEmpresas;
    }

    private boolean existeNit(String nit) {
        String sql = "SELECT 1 FROM empresas WHERE nit = ?";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nit);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Devuelve true si encontró el NIT
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
