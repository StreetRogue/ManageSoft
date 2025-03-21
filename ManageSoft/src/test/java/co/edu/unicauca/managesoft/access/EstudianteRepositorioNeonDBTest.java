package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.Estudiante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class EstudianteRepositorioNeonDBTest {
    private EstudianteRepositorioNeonDB repositorio;
    private Connection connectionMock;
    private PreparedStatement stmtMock;
    private ResultSet resultSetMock;

    @BeforeEach
    public void setUp() throws SQLException {
        connectionMock = Mockito.mock(Connection.class);
        stmtMock = Mockito.mock(PreparedStatement.class);
        resultSetMock = Mockito.mock(ResultSet.class);

        repositorio = Mockito.spy(new EstudianteRepositorioNeonDB());

        doReturn(connectionMock).when(repositorio).conectar();

        when(connectionMock.prepareStatement(anyString(), eq(PreparedStatement.RETURN_GENERATED_KEYS)))
                .thenReturn(stmtMock);
        when(connectionMock.prepareStatement(anyString())).thenReturn(stmtMock);

        when(stmtMock.executeUpdate()).thenReturn(1);
        when(stmtMock.getGeneratedKeys()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getInt(1)).thenReturn(100);
    }


    @Test
    public void testBuscarEstudiantePorUsuario() throws SQLException {
        when(stmtMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getLong("codigoEstudiante")).thenReturn(12345L);
        when(resultSetMock.getString("nombreEstudiante")).thenReturn("Juan");
        when(resultSetMock.getString("apellidoEstudiante")).thenReturn("Pérez");
        when(resultSetMock.getString("correoEstudiante")).thenReturn("juan@email.com");
        when(resultSetMock.getString("nombre_usuario")).thenReturn("juan123");
        when(resultSetMock.getString("contrasena")).thenReturn("pass123");

        Estudiante estudiante = repositorio.buscarEstudiante("juan123");

        assertNotNull(estudiante, "El estudiante debería existir");
        assertEquals("12345", estudiante.getNombreEstudiante(), "El código no coincide"); // Compara con el código
    }


    @Test
    public void testBuscarEstudiantePorUsuarioYContrasena() throws SQLException {
        when(stmtMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getLong("codigoEstudiante")).thenReturn(12345L);
        when(resultSetMock.getString("nombreEstudiante")).thenReturn("Juan");
        when(resultSetMock.getString("apellidoEstudiante")).thenReturn("Pérez");
        when(resultSetMock.getString("correoEstudiante")).thenReturn("juan@email.com");
        when(resultSetMock.getString("nombre_usuario")).thenReturn("juan123");
        when(resultSetMock.getString("contrasena")).thenReturn("pass123");

        Estudiante estudiante = repositorio.buscarEstudiante("juan123", "pass123");

        assertNotNull(estudiante, "El estudiante debería existir");
        assertEquals("12345", estudiante.getNombreEstudiante(), "El código no coincide"); // Compara con el código
    }


    @Test
    public void testExisteSimca() throws SQLException {
        when(stmtMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);

        boolean existe = repositorio.existeSimca("12345");
        assertTrue(existe, "El estudiante debería existir en la base de datos");
    }
}

