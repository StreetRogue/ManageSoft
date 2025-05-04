package co.edu.unicauca.microcoordinador;

import co.edu.unicauca.microcoordinador.access.repositories.ICoordinadorRepositorio;
import co.edu.unicauca.microcoordinador.access.repositories.IProyectoRepositorio;
import co.edu.unicauca.microcoordinador.entities.Coordinador;
import co.edu.unicauca.microcoordinador.entities.Empresa;
import co.edu.unicauca.microcoordinador.entities.EnumEstadoProyecto;
import co.edu.unicauca.microcoordinador.entities.Proyecto;
import co.edu.unicauca.microcoordinador.infra.dto.CambioEstadoDto;
import co.edu.unicauca.microcoordinador.infra.dto.DetalleProyectoDto;
import co.edu.unicauca.microcoordinador.infra.dto.ProyectoResumenDto;
import co.edu.unicauca.microcoordinador.services.impl.CoordinadorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import static org.mockito.Mockito.eq;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CoordinadorServiceTest {
    @Mock
    private ICoordinadorRepositorio coordinadorRepositorio;

    @Mock
    private IProyectoRepositorio proyectoRepositorio;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private CoordinadorService coordinadorService;

    @Test
    void obtenerProyectosDelCoordinador_deberiaRetornarProyectosResumen() {
        // Arrange
        Long idCoordinador = 1L;

        Proyecto proyecto1 = new Proyecto();
        proyecto1.setIdProyecto(101L);
        proyecto1.setNombreProyecto("IA con ChatGPT");
        proyecto1.setEstadoProyecto(EnumEstadoProyecto.RECIBIDO);

        Proyecto proyecto2 = new Proyecto();
        proyecto2.setIdProyecto(102L);
        proyecto2.setNombreProyecto("IA con Python");
        proyecto2.setEstadoProyecto(EnumEstadoProyecto.ACEPTADO);

        Coordinador coordinador = new Coordinador();
        coordinador.setIdCoordinador(idCoordinador);
        coordinador.setProyectosAsignados(List.of(proyecto1, proyecto2));

        when(coordinadorRepositorio.findById(idCoordinador))
                .thenReturn(Optional.of(coordinador));

        // Act
        List<ProyectoResumenDto> resultado = coordinadorService.obtenerProyectosDelCoordinador(idCoordinador);

        // Assert
        assertEquals(2, resultado.size());

        assertEquals("IA con ChatGPT", resultado.get(0).getNombre());
        assertEquals("RECIBIDO", resultado.get(0).getEstado());

        assertEquals("IA con Python", resultado.get(1).getNombre());
        assertEquals("ACEPTADO", resultado.get(1).getEstado());

        verify(coordinadorRepositorio).findById(idCoordinador);
    }

    @Test
    void testObtenerDetalleProyecto_exitoso() {
        // Arrange
        Long idProyecto = 1L;

        Empresa empresa = new Empresa();
        empresa.setId(10L);
        empresa.setNombreEmpresa("Tech Solutions");

        Coordinador coordinador = new Coordinador();
        coordinador.setIdCoordinador(5L);
        coordinador.setNombre("Ana");
        coordinador.setApellido("Gómez");

        Proyecto proyecto = new Proyecto();
        proyecto.setIdProyecto(idProyecto);
        proyecto.setNombreProyecto("Plataforma E-learning");
        proyecto.setEstadoProyecto(EnumEstadoProyecto.EJECUCION);
        proyecto.setDescripcionProyecto("Plataforma educativa para colegios.");
        proyecto.setEmpresa(empresa);
        proyecto.setCoordinador(coordinador); // ← Aquí corriges el NPE

        when(proyectoRepositorio.findById(idProyecto)).thenReturn(Optional.of(proyecto));

        // Act
        DetalleProyectoDto resultado = coordinadorService.obtenerDetalleProyecto(idProyecto);

        // Assert
        assertNotNull(resultado);
        assertEquals("Plataforma E-learning", resultado.getNombre());
        assertEquals("EJECUCION", resultado.getEstado());
        assertEquals("Tech Solutions", resultado.getNombreEmpresa());
    }

    @Test
    void cambiarEstadoProyecto_deberiaActualizarEstadoYNotificar() {
        // Arrange
        Long idProyecto = 1L;
        String nuevoEstado = "ACEPTADO";
        String comentario = "Proyecto aceptado para ejecución.";

        // Crear el DTO de cambio de estado
        CambioEstadoDto cambioEstadoDto = new CambioEstadoDto(nuevoEstado, comentario);

        // Crear el proyecto y asignar estado inicial
        Proyecto proyecto = new Proyecto();
        proyecto.setIdProyecto(idProyecto);
        proyecto.setNombreProyecto("Plataforma E-learning");
        proyecto.setEstadoProyecto(EnumEstadoProyecto.RECIBIDO);
        proyecto.setComentario("Comentario inicial");

        // Simular el repositorio
        when(proyectoRepositorio.findById(idProyecto)).thenReturn(Optional.of(proyecto));

        // Act
        coordinadorService.cambiarEstadoProyecto(idProyecto, cambioEstadoDto);

        // Assert
        // Verificar que el estado del proyecto ha cambiado
        assertEquals(EnumEstadoProyecto.ACEPTADO, proyecto.getEstadoProyecto());
        assertEquals(comentario, proyecto.getComentario());

        // Verificar que el proyecto se ha guardado en el repositorio
        verify(proyectoRepositorio).save(proyecto);

        // Verificar que el mensaje fue enviado a RabbitMQ
        verify(rabbitTemplate).convertAndSend(eq("ColaNotificacionCoordinador"), anyString());
    }


}
