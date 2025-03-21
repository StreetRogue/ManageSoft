package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.Correo;
import co.edu.unicauca.managesoft.entities.Estudiante;
import co.edu.unicauca.managesoft.entities.Proyecto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NotificacionRepositorioNeonDBTest {

    private NotificacionRepositorioNeonDB repositorio;
    private Connection connectionMock;
    private PreparedStatement stmtSelectCoordinadorMock;
    private PreparedStatement stmtInsertSolicitudMock;
    private ResultSet resultSetMock;

    @BeforeEach
    public void setUp() throws SQLException {
        // Crear mocks para Connection, PreparedStatement y ResultSet
        connectionMock = Mockito.mock(Connection.class);
        stmtSelectCoordinadorMock = Mockito.mock(PreparedStatement.class);
        stmtInsertSolicitudMock = Mockito.mock(PreparedStatement.class);
        resultSetMock = Mockito.mock(ResultSet.class);

        // Crear el repositorio
        repositorio = Mockito.spy(new NotificacionRepositorioNeonDB());

        // Mockear el método conectar() para devolver la conexión simulada
        doReturn(connectionMock).when(repositorio).conectar();

        // Configurar los mocks para las operaciones de base de datos
        when(connectionMock.prepareStatement(anyString())).thenReturn(stmtSelectCoordinadorMock, stmtInsertSolicitudMock);
    }

    @Test
    public void testEnviarCorreo_Exitoso() throws SQLException {
        // Arrange: Crear objetos de prueba
        Correo correo = new Correo("Asunto del correo", "Mensaje del correo", "coordinador@example.com");
        Estudiante estudiante = new Estudiante("Juan", "Pérez", "12345", "juan@example.com");
        Proyecto proyecto = new Proyecto("Proyecto de Innovación", "Resumen del proyecto",
                "Objetivo del proyecto", "Descripción detallada",
                "12", "50000");
        proyecto.setIdProyecto(1);

        // Simular la consulta del coordinador
        when(stmtSelectCoordinadorMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true); // Simular que se encontró el coordinador
        when(resultSetMock.getInt("id")).thenReturn(1); // Simular el ID del coordinador

        // Simular la inserción de la solicitud
        when(stmtInsertSolicitudMock.executeUpdate()).thenReturn(1); // Simular una inserción exitosa

        // Act: Enviar el correo
        boolean resultado = repositorio.enviarCorreo(correo, estudiante, proyecto);

        // Assert: Verificar que se envió correctamente
        assertTrue(resultado, "El correo debería haberse enviado correctamente");
        verify(stmtSelectCoordinadorMock, times(1)).executeQuery();
        verify(stmtInsertSolicitudMock, times(1)).executeUpdate();
    }

    @Test
    public void testEnviarCorreo_CoordinadorNoEncontrado() throws SQLException {
        // Arrange: Crear objetos de prueba
        Correo correo = new Correo("Asunto del correo", "Mensaje del correo", "coordinador@example.com");
        Estudiante estudiante = new Estudiante("Juan", "Pérez", "12345", "juan@example.com");
        Proyecto proyecto = new Proyecto("Proyecto de Innovación", "Resumen del proyecto",
                "Objetivo del proyecto", "Descripción detallada",
                "12", "50000");
        proyecto.setIdProyecto(1);

        // Simular que no se encontró el coordinador
        when(stmtSelectCoordinadorMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(false); // Simular que no se encontró el coordinador

        // Act: Enviar el correo
        boolean resultado = repositorio.enviarCorreo(correo, estudiante, proyecto);

        // Assert: Verificar que no se envió debido a que no se encontró el coordinador
        assertFalse(resultado, "El correo no debería haberse enviado porque no se encontró el coordinador");
        verify(stmtSelectCoordinadorMock, times(1)).executeQuery();
        verify(stmtInsertSolicitudMock, never()).executeUpdate(); // No se debe llamar a la inserción
    }

}