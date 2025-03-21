package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.Estudiante;
import co.edu.unicauca.managesoft.entities.enumTipoUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EstudianteRepositorioArrayTest {

    private EstudianteRepositorioArray repositorio;

    @BeforeEach
    public void setUp() {
        repositorio = new EstudianteRepositorioArray();
    }

    @Test
    public void testGuardarEstudiante() {
        // Arrange: Crear un estudiante
        Estudiante nuevoEstudiante = new Estudiante("Lionel", "Messi", "101112", "messi@example.com",
                "lmessi", "goat2023");

        // Act: Guardar el estudiante
        boolean resultado = repositorio.guardar(nuevoEstudiante);

        // Assert: Verificar que se guardó correctamente
        assertTrue(resultado, "El estudiante debería haberse guardado correctamente");
    }

    @Test
    public void testGuardarEstudianteConCodigoSimcaExistente() {
        // Arrange: Crear un estudiante
        Estudiante estudianteExistente = new Estudiante("Cristiano", "Ronaldo", "789101", "cr7@example.com",
                "cr7", "siu2023");
        repositorio.guardar(estudianteExistente);

        // Intentar guardar otro estudiante con el mismo código Simca
        Estudiante nuevoEstudiante = new Estudiante("Neymar", "Jr", "789101", "neymar@example.com",
                "neymarjr", "drible2023");

        // Act: Intentar guardar el nuevo estudiante
        boolean resultado = repositorio.guardar(nuevoEstudiante);

        // Assert: Verificar que no se permitió guardar
        assertFalse(resultado, "No debería permitir guardar un estudiante con un código Simca ya existente");
    }

    @Test
    public void testBuscarEstudiantePorNombreUsuario() {
        // Arrange: Crear un estudiante
        Estudiante estudiante = new Estudiante("Kylian", "Mbappe", "131415", "mbappe@example.com",
                "kmbappe", "speed2023");
        repositorio.guardar(estudiante);

        // Act: Buscar el estudiante por su nombre de usuario
        Estudiante resultado = repositorio.buscarEstudiante("kmbappe");

        // Assert: Verificar que se encontró el estudiante
        assertNotNull(resultado, "El estudiante Kylian Mbappé debería existir en el repositorio");
        assertEquals("Kylian", resultado.getNombreEstudiante(), "El nombre del estudiante no coincide");
    }

    @Test
    public void testBuscarEstudiantePorCredenciales() {
        // Arrange: Crear un estudiante con nombre de futbolista
        Estudiante estudiante = new Estudiante("Erling", "Haaland", "161718", "haaland@example.com",
                "ehaaland", "gol2023");
        repositorio.guardar(estudiante);

        // Act: Buscar el estudiante por su nombre de usuario y contraseña
        Estudiante resultado = repositorio.buscarEstudiante("ehaaland", "gol2023");

        // Assert: Verificar que se encontró el estudiante
        assertNotNull(resultado, "El estudiante Erling Haaland debería existir en el repositorio");
        assertEquals("Erling", resultado.getNombreEstudiante(), "El nombre del estudiante no coincide");
    }

    @Test
    public void testListarEstudiantes() {
        // Limpiar la lista de estudiantes antes de la prueba
        repositorio.listarEstudiantes().clear();

        // Arrange: Crear varios estudiantes con nombres de futbolistas
        Estudiante estudiante1 = new Estudiante("Lionel", "Messi", "101112", "messi@example.com",
                "lmessi", "goat2023");
        Estudiante estudiante2 = new Estudiante("Cristiano", "Ronaldo", "789101", "cr7@example.com",
                "cr7", "siu2023");
        Estudiante estudiante3 = new Estudiante("Neymar", "Jr", "123456", "neymar@example.com",
                "neymarjr", "drible2023");

        repositorio.guardar(estudiante1);
        repositorio.guardar(estudiante2);
        repositorio.guardar(estudiante3);

        // Act: Listar todos los estudiantes
        List<Estudiante> estudiantes = repositorio.listarEstudiantes();

        // Assert: Verificar que la lista no esté vacía y tenga la cantidad correcta de estudiantes
        assertFalse(estudiantes.isEmpty(), "La lista de estudiantes no debería estar vacía");
        assertEquals(3, estudiantes.size(), "Debería haber 3 estudiantes en la lista");
    }
}
