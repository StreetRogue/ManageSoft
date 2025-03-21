package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.Empresa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.sql.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmpresaRepositorioNeonDBTest {

    private EmpresaRepositorioNeonDB repositorio;
    private Connection connectionMock;
    private PreparedStatement stmtUsuarioMock;
    private PreparedStatement stmtEmpresaMock;
    private PreparedStatement stmtSelectMock;
    private ResultSet resultSetMock;

    @BeforeEach
    public void setUp() throws SQLException {
        // Crear mocks para Connection, PreparedStatement y ResultSet
        connectionMock = Mockito.mock(Connection.class);
        stmtSelectMock = Mockito.mock(PreparedStatement.class); // Debe ser PreparedStatement
        stmtUsuarioMock = Mockito.mock(PreparedStatement.class);
        stmtEmpresaMock = Mockito.mock(PreparedStatement.class);
        resultSetMock = Mockito.mock(ResultSet.class);

        // Crear el repositorio como un espía
        repositorio = Mockito.spy(new EmpresaRepositorioNeonDB());

        // Mockear el método conectar() para devolver la conexión simulada
        doReturn(connectionMock).when(repositorio).conectar();

        // Configurar los mocks para las operaciones de base de datos
        when(connectionMock.prepareStatement(anyString())).thenReturn(stmtSelectMock, stmtUsuarioMock, stmtEmpresaMock);
        when(stmtSelectMock.executeQuery()).thenReturn(resultSetMock); // Asegurar que devuelve el ResultSet
        when(stmtUsuarioMock.executeUpdate()).thenReturn(1);
        when(stmtUsuarioMock.getGeneratedKeys()).thenReturn(resultSetMock);
        when(stmtEmpresaMock.executeUpdate()).thenReturn(1);
    }




    @Test
    public void testGuardar_EmpresaConNitExistente() throws SQLException {
        // Arrange: Crear una nueva empresa con un NIT que ya existe
        Empresa nuevaEmpresa = new Empresa("12345", "Nueva Empresa", "contacto@nuevaempresa.com",
                "Tecnología", "1234567890", "Juan", "Pérez", "Gerente",
                "nuevaempresa", "password123");

        // Simular que ya existe un NIT
        when(resultSetMock.next()).thenReturn(true); // Existe el NIT

        // Act: Intentar guardar la empresa
        boolean resultado = repositorio.guardar(nuevaEmpresa);

        // Assert: Verificar que no se guardó debido a un NIT duplicado
        assertFalse(resultado, "No debería permitir guardar una empresa con un NIT ya existente");
        verify(stmtSelectMock, times(1)).executeQuery();
        verify(stmtUsuarioMock, never()).executeUpdate(); // No se debe llamar a la inserción del usuario
    }

    @Test
    public void testBuscarEmpresa_PorCredenciales() throws SQLException {
        // Arrange: Crear una empresa y simular la consulta
        Empresa empresa = new Empresa("12345", "Nueva Empresa", "contacto@nuevaempresa.com",
                "Tecnología", "1234567890", "Juan", "Pérez", "Gerente",
                "nuevaempresa", "password123");

        when(stmtSelectMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true); // Simular que se encontró la empresa
        when(resultSetMock.getString("nit")).thenReturn("12345");
        when(resultSetMock.getString("nombre")).thenReturn("Nueva Empresa");
        when(resultSetMock.getString("email")).thenReturn("contacto@nuevaempresa.com");
        when(resultSetMock.getString("sector")).thenReturn("Tecnología");
        when(resultSetMock.getString("telefono_contacto")).thenReturn("1234567890");
        when(resultSetMock.getString("nombre_contacto")).thenReturn("Juan");
        when(resultSetMock.getString("apellido_contacto")).thenReturn("Pérez");
        when(resultSetMock.getString("cargo_contacto")).thenReturn("Gerente");
        when(resultSetMock.getString("nombre_usuario")).thenReturn("nuevaempresa");
        when(resultSetMock.getString("contrasena")).thenReturn("password123");

        // Act: Buscar la empresa por nombre de usuario y contraseña
        Empresa resultado = repositorio.buscarEmpresa("nuevaempresa", "password123");

        // Assert: Verificar que se encontró la empresa
        assertNotNull(resultado, "La empresa debería existir en el repositorio");
        assertEquals("12345", resultado.getNitEmpresa(), "El NIT de la empresa no coincide");
    }


    @Test
    public void testExisteNit() throws SQLException {
        // Arrange: Simular la consulta de existencia de NIT
        when(stmtSelectMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true); // Simular que el NIT existe

        // Act: Verificar si existe un NIT
        boolean resultado = repositorio.existeNit("12345");

        // Assert: Verificar que el NIT existe
        assertTrue(resultado, "El NIT debería existir en el repositorio");
        verify(stmtSelectMock, times(1)).executeQuery();
    }
}