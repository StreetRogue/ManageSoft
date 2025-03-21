package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.Empresa;
import co.edu.unicauca.managesoft.entities.Proyecto; // Importa la clase Proyecto
import co.edu.unicauca.managesoft.entities.enumTipoUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List; // Importa la clase List

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*; // Importa los métodos estáticos de Mockito

public class EmpresaRepositorioArrayTest {

    private EmpresaRepositorioArray repositorio;
    private IProyectoRepositorio repositorioProyectoMock;

    @BeforeEach
    public void setUp() {
        repositorio = new EmpresaRepositorioArray();
        repositorioProyectoMock = Mockito.mock(IProyectoRepositorio.class);
        repositorio.setRepositorioProyecto(repositorioProyectoMock);
    }

    @Test
    public void testGuardarEmpresa() {
        // Arrange
        Empresa nuevaEmpresa = new Empresa("12345", "Nueva Empresa", "contacto@nuevaempresa.com",
                "Tecnología", "1234567890", "Juan", "Pérez", "Gerente",
                "nuevaempresa", "password123");

        // Act
        boolean resultado = repositorio.guardar(nuevaEmpresa);

        // Assert
        assertTrue(resultado, "La empresa debería haberse guardado correctamente");
    }

    @Test
    public void testGuardarEmpresaConNitExistente() {
        // Arrange
        Empresa empresaExistente = new Empresa("123", "TechCorp", "contacto@techcorp.com",
                "Tecnología", "1234567890", "Juan", "Pérez",
                "Gerente", "techcorp", "password123");
        repositorio.guardar(empresaExistente);

        Empresa nuevaEmpresa = new Empresa("123", "Otra Empresa", "contacto@otraempresa.com",
                "Tecnología", "9876543210", "Ana", "Gómez",
                "Directora", "otraempresa", "password456");

        // Act
        boolean resultado = repositorio.guardar(nuevaEmpresa);

        // Assert
        assertFalse(resultado, "No debería permitir guardar una empresa con un NIT ya existente");
    }

    @Test
    public void testBuscarEmpresaPorNombreUsuario() {
        // Arrange
        Empresa empresa = new Empresa("123", "TechCorp", "contacto@techcorp.com",
                "Tecnología", "1234567890", "Juan", "Pérez",
                "Gerente", "techcorp", "password123");
        repositorio.guardar(empresa);

        // Act
        Empresa resultado = repositorio.buscarEmpresa("techcorp");

        // Assert
        assertNotNull(resultado, "La empresa debería existir en el repositorio");
        assertEquals("TechCorp", resultado.getNombreEmpresa(), "El nombre de la empresa no coincide");
    }

    @Test
    public void testBuscarEmpresaPorCredenciales() {
        // Arrange
        Empresa empresa = new Empresa("123", "TechCorp", "contacto@techcorp.com",
                "Tecnología", "1234567890", "Juan", "Pérez",
                "Gerente", "techcorp", "password123");
        repositorio.guardar(empresa);

        // Act
        Empresa resultado = repositorio.buscarEmpresa("techcorp", "password123");

        // Assert
        assertNotNull(resultado, "La empresa debería existir en el repositorio");
        assertEquals("TechCorp", resultado.getNombreEmpresa(), "El nombre de la empresa no coincide");
    }

    @Test
    public void testListarEmpresas() {
        // Limpiar la lista de empresas antes de la prueba
        repositorio.listarEmpresas().clear();

        // Arrange
        Empresa empresa1 = new Empresa("123", "TechCorp", "contacto@techcorp.com",
                "Tecnología", "1234567890", "Juan", "Pérez",
                "Gerente", "techcorp", "password123");
        Empresa empresa2 = new Empresa("456", "EcoSolutions", "info@ecosolutions.com",
                "Medio Ambiente", "9876543210", "Ana", "Gómez",
                "Directora", "ecosolutions", "password456");
        repositorio.guardar(empresa1);
        repositorio.guardar(empresa2);

        // Act
        List<Empresa> empresas = repositorio.listarEmpresas();

        // Assert
        assertFalse(empresas.isEmpty(), "La lista de empresas no debería estar vacía");
        assertEquals(2, empresas.size(), "Debería haber 2 empresas en la lista");
    }

    @Test
    public void testAgregarProyecto() {
        // Arrange
        Empresa empresa = new Empresa("123", "TechCorp", "contacto@techcorp.com",
                "Tecnología", "1234567890", "Juan", "Pérez",
                "Gerente", "techcorp", "password123");
        repositorio.guardar(empresa);

        when(repositorioProyectoMock.guardarProyecto(any(), eq(empresa))).thenReturn(true);

        // Act
        boolean resultado = empresa.agregarProyecto(repositorioProyectoMock, "Proyecto 1", "Resumen",
                "Objetivo", "Descripción", "6", "10000");

        // Assert
        assertTrue(resultado, "El proyecto debería haberse agregado correctamente");
        verify(repositorioProyectoMock, times(1)).guardarProyecto(any(), eq(empresa));
    }

    @Test
    public void testListarProyectos() {
        // Arrange
        Empresa empresa = new Empresa("123", "TechCorp", "contacto@techcorp.com",
                "Tecnología", "1234567890", "Juan", "Pérez",
                "Gerente", "techcorp", "password123");
        repositorio.guardar(empresa);

        // Configurar el repositorio de proyectos en la empresa
        empresa.setRepositorioProyectos(repositorioProyectoMock);

        // Simular el comportamiento del repositorio de proyectos
        when(repositorioProyectoMock.listarProyectos(empresa)).thenReturn(List.of());

        // Act
        List<Proyecto> proyectos = empresa.listarProyectos();

        // Assert
        assertNotNull(proyectos, "La lista de proyectos no debería ser nula");
        verify(repositorioProyectoMock, times(1)).listarProyectos(empresa);
    }
}