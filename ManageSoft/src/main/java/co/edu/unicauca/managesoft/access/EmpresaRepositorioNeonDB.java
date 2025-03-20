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

public class EmpresaRepositorioNeonDB implements IEmpresaRepositorio {

    private static final String url = "jdbc:postgresql://ep-twilight-rice-a5meykz5-pooler.us-east-2.aws.neon.tech/neondb?sslmode=require";
    private static final String user = "neondb_owner";
    private static final String password = "npg_J9zkqVtWupl1";

    private static IProyectoRepositorio repositorioProyecto;

// Método para obtener la conexión con usuario y contraseña
    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public boolean guardar(Empresa nuevaEmpresa) {
        if (existeNit(nuevaEmpresa.getNitEmpresa())) {
            return false;
        }

        String sqlSelectUsuario = "SELECT id FROM Usuario WHERE nombre_usuario = ? AND contrasena = ?";
        String sqlEmpresa = "INSERT INTO Empresa (nit, id_usuario, nombre, email, sector, telefono_contacto, nombre_contacto, apellido_contacto, cargo_contacto) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = conectar(); PreparedStatement stmtSelectUsuario = conn.prepareStatement(sqlSelectUsuario); PreparedStatement stmtEmpresa = conn.prepareStatement(sqlEmpresa)) {

            conn.setAutoCommit(false);

            // Obtener el ID del usuario
            stmtSelectUsuario.setString(1, nuevaEmpresa.getNombreUsuario());
            stmtSelectUsuario.setString(2, nuevaEmpresa.getContrasenaUsuario());

            try (ResultSet rs = stmtSelectUsuario.executeQuery()) {
                if (!rs.next()) {
                    conn.rollback();
                    return false;
                }

                int idUsuario = rs.getInt("id");

                // Insertar empresa
                stmtEmpresa.setString(1, nuevaEmpresa.getNitEmpresa());
                stmtEmpresa.setInt(2, idUsuario);
                stmtEmpresa.setString(3, nuevaEmpresa.getNombreEmpresa());
                stmtEmpresa.setString(4, nuevaEmpresa.getEmailEmpresa());
                stmtEmpresa.setString(5, nuevaEmpresa.getSectorEmpresa());
                stmtEmpresa.setString(6, nuevaEmpresa.getContactoEmpresa());
                stmtEmpresa.setString(7, nuevaEmpresa.getNombreContactoEmpresa());
                stmtEmpresa.setString(8, nuevaEmpresa.getApellidoContactoEmpresa());
                stmtEmpresa.setString(9, nuevaEmpresa.getCargoContactoEmpresa());

                if (stmtEmpresa.executeUpdate() > 0) {
                    conn.commit();
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
    public Empresa buscarEmpresa(String nombreUsuario) {
        String sql = "SELECT e.nit, e.nombre, e.email, e.sector, e.telefono_contacto, e.nombre_contacto, e.apellido_contacto, e.cargo_contacto, u.nombre_usuario, u.contrasena "
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
                        rs.getString("telefono_contacto"),
                        rs.getString("nombre_contacto"),
                        rs.getString("apellido_contacto"),
                        rs.getString("cargo_contacto"),
                        rs.getString("nombre_usuario"),
                        rs.getString("contrasena")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Empresa buscarEmpresa(String nombreUsuario, String contrasenaUsuario) {
        String sql = "SELECT e.nit, e.nombre, e.email, e.sector, e.telefono_contacto, e.nombre_contacto, e.apellido_contacto, e.cargo_contacto, u.nombre_usuario, u.contrasena "
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
                        rs.getString("telefono_contacto"),
                        rs.getString("nombre_contacto"),
                        rs.getString("apellido_contacto"),
                        rs.getString("cargo_contacto"),
                        rs.getString("nombre_usuario"),
                        rs.getString("contrasena")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Empresa> listarEmpresas() {
        List<Empresa> listaEmpresas = new ArrayList<>();
        String sql = "SELECT e.nit, e.nombre, e.email, e.sector, e.telefono_contacto, e.nombre_contacto, e.apellido_contacto, e.cargo_contacto, u.nombre_usuario, u.contrasena "
                + "FROM Empresa e "
                + "JOIN Usuario u ON e.id_usuario = u.id";

        try (Connection conn = conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Empresa empresa = new Empresa(
                        rs.getString("nit"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("sector"),
                        rs.getString("telefono_contacto"),
                        rs.getString("nombre_contacto"),
                        rs.getString("apellido_contacto"),
                        rs.getString("cargo_contacto"),
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

    @Override
    public void setRepositorioProyecto(IProyectoRepositorio repositorioProyecto) {
        this.repositorioProyecto = repositorioProyecto;
    }

    private boolean existeNit(String nit) {
        String sql = "SELECT 1 FROM Empresa WHERE nit = ?";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nit);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
