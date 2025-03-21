package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.Empresa;
import co.edu.unicauca.managesoft.entities.Proyecto;
import co.edu.unicauca.managesoft.entities.EstadoRecibido;
import co.edu.unicauca.managesoft.entities.IEstadoProyecto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProyectoRepositorioNeonDBTest {

    private ProyectoRepositorioNeonDB repositorio;
    private Connection connectionMock;
    private PreparedStatement preparedStatementMock;
    private ResultSet resultSetMock;
    private Empresa empresaMock;

    @BeforeEach
    public void setUp() throws SQLException {
        // Crear mocks para Connection, PreparedStatement y ResultSet
        connectionMock = Mockito.mock(Connection.class);
        preparedStatementMock = Mockito.mock(PreparedStatement.class);
        resultSetMock = Mockito.mock(ResultSet.class);

        // Crear el repositorio
        repositorio = Mockito.spy(new ProyectoRepositorioNeonDB());

        // Mockear el método conectar() para devolver la conexión simulada
        doReturn(connectionMock).when(repositorio).conectar();

        // Mock de la empresa
        empresaMock = Mockito.mock(Empresa.class);
        when(empresaMock.getNitEmpresa()).thenReturn("12345");
    }

    @Test
    public void testGuardarProyecto() throws SQLException {
        // Arrange: Crear un proyecto y configurar el mock
        Proyecto proyecto = new Proyecto("Proyecto de Innovación", "Resumen del proyecto",
                "Objetivo del proyecto", "Descripción detallada",
                "12", "50000");

        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeUpdate()).thenReturn(1); // Simular una inserción exitosa

        // Act: Guardar el proyecto
        boolean resultado = repositorio.guardarProyecto(proyecto, empresaMock);

        // Assert: Verificar que se guardó correctamente
        assertTrue(resultado, "El proyecto debería haberse guardado correctamente");
        verify(preparedStatementMock, times(1)).executeUpdate();
    }

    @Test
    public void testListarProyectos() throws SQLException {
        // Arrange: Configurar el mock para simular la consulta de proyectos
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true, false); // Simular un solo resultado
        when(resultSetMock.getInt("id")).thenReturn(1);
        when(resultSetMock.getString("nombre")).thenReturn("Proyecto A");
        when(resultSetMock.getString("resumen")).thenReturn("Resumen A");
        when(resultSetMock.getString("objetivos")).thenReturn("Objetivo A");
        when(resultSetMock.getString("descripcion")).thenReturn("Descripción A");
        when(resultSetMock.getInt("tiempo_maximo_meses")).thenReturn(12);
        when(resultSetMock.getFloat("presupuesto")).thenReturn(10000.0f);
        when(resultSetMock.getString("fecha")).thenReturn("2023-10-01");
        when(resultSetMock.getString("estado")).thenReturn("RECIBIDO");

        // Act: Listar proyectos de la empresa
        List<Proyecto> proyectos = repositorio.listarProyectos(empresaMock);

        // Assert: Verificar que se listó correctamente
        assertFalse(proyectos.isEmpty(), "La lista de proyectos no debería estar vacía");
        assertEquals(1, proyectos.size(), "Debería haber 1 proyecto asociado a la empresa");
        assertEquals("Proyecto A", proyectos.get(0).getNombreProyecto(), "El nombre del proyecto no coincide");
    }

    @Test
    public void testListarProyectosGeneral() throws SQLException {
        // Arrange: Configurar el mock para simular la consulta de todos los proyectos
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true, false); // Simular un solo resultado
        when(resultSetMock.getInt("id")).thenReturn(1);
        when(resultSetMock.getString("nombre_proyecto")).thenReturn("Proyecto A");
        when(resultSetMock.getString("resumen")).thenReturn("Resumen A");
        when(resultSetMock.getString("objetivos")).thenReturn("Objetivo A");
        when(resultSetMock.getString("descripcion")).thenReturn("Descripción A");
        when(resultSetMock.getInt("tiempo_maximo_meses")).thenReturn(12);
        when(resultSetMock.getFloat("presupuesto")).thenReturn(10000.0f);
        when(resultSetMock.getString("fecha")).thenReturn("2023-10-01");
        when(resultSetMock.getString("nombre_empresa")).thenReturn("TechCorp");
        when(resultSetMock.getString("estado")).thenReturn("RECIBIDO");

        // Act: Listar todos los proyectos
        List<Proyecto> proyectos = repositorio.listarProyectosGeneral();

        // Assert: Verificar que se listaron correctamente
        assertFalse(proyectos.isEmpty(), "La lista de proyectos no debería estar vacía");
        assertEquals(1, proyectos.size(), "Debería haber 1 proyecto en total");
        assertEquals("Proyecto A", proyectos.get(0).getNombreProyecto(), "El nombre del proyecto no coincide");
    }
}