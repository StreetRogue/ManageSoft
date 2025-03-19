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

// Método para obtener la conexión con usuario y contraseña
    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public boolean guardar(Empresa nuevaEmpresa) {
        if (existeNit(nuevaEmpresa.getNitEmpresa())) {
            return false;
        }

        String sql = "INSERT INTO Empresa (nit, id_usuario, nombre, email, sector, telefono, representante_nombre, representante_apellido, cargo) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            stmt.setString(1, nuevaEmpresa.getNitEmpresa());
            stmt.setInt(2, nuevaEmpresa.getIdUsuario());
            stmt.setString(3, nuevaEmpresa.getNombreEmpresa());
            stmt.setString(4, nuevaEmpresa.getEmailEmpresa());
            stmt.setString(5, nuevaEmpresa.getSectorEmpresa());
            stmt.setString(6, nuevaEmpresa.getContactoEmpresa());
            stmt.setString(7, nuevaEmpresa.getNombreContactoEmpresa());
            stmt.setString(8, nuevaEmpresa.getApellidoContactoEmpresa());
            stmt.setString(9, nuevaEmpresa.getCargoContactoEmpresa());

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
    public Empresa buscarEmpresa(String nombreUsuario) {
        String sql = "SELECT e.nit, e.nombre, e.email, e.sector, e.telefono, e.representante_nombre, e.representante_apellido, e.cargo, u.nombre_usuario, u.contrasena "
                + "FROM Empresa e INNER JOIN Usuario u ON e.id_usuario = u.id WHERE u.nombre_usuario = ?";

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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
        @Override
    public Empresa buscarEmpresa(String nombreUsuario, String contrasenaUsuario) {
        String sql = "SELECT e.nit, e.nombre, e.email, e.sector, e.telefono, e.representante_nombre, e.representante_apellido, e.cargo, u.nombre_usuario, u.contrasena "
                + "FROM Empresa e INNER JOIN Usuario u ON e.id_usuario = u.id "
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    

    @Override
    public List<Empresa> listarEmpresas() {
        List<Empresa> listaEmpresas = new ArrayList<>();
        String sql = "SELECT e.nit, e.nombre, e.email, e.sector, e.telefono, e.representante_nombre, e.representante_apellido, e.cargo, u.nombre_usuario, u.contrasena "
                + "FROM Empresa e JOIN Usuario u ON e.id_usuario = u.id";

        try (Connection conn = conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                listaEmpresas.add(new Empresa(
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
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaEmpresas;
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
