package co.edu.unicauca.microcoordinador.access.repositories;

import co.edu.unicauca.microcoordinador.entities.EnumEstadoProyecto;
import co.edu.unicauca.microcoordinador.entities.Proyecto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import co.edu.unicauca.microcoordinador.entities.Coordinador;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ICoordinadorRepositorio coordinadorRepositorio;
    @Autowired
    private IProyectoRepositorio proyectoRepositorio;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        coordinadorRepositorio.deleteAll();
        proyectoRepositorio.deleteAll();  // Asegúrate de limpiar la tabla de proyectos también

        // Crear coordinadores
        Coordinador coordinador1 = new Coordinador("SIMCA001", "Lionel", "Messi", "messi@fcbarcelona.com", "987654321", "messiPass123");
        Coordinador coordinador2 = new Coordinador("SIMCA002", "Cristiano", "Ronaldo", "cristiano@juventus.com", "123456789", "cristianoPass456");

        coordinadorRepositorio.save(coordinador1);
        coordinadorRepositorio.save(coordinador2);

        // Crear proyectos sin la relación con la empresa
        Proyecto proyecto1 = new Proyecto();
        proyecto1.setNombreProyecto("Sistema de Gestión");
        proyecto1.setDescripcionProyecto("Sistema para gestionar recursos");
        proyecto1.setResumenProyecto("Resumen del sistema");
        proyecto1.setObjetivoProyecto("Objetivos claros");
        proyecto1.setMaximoMesesProyecto("30");
        proyecto1.setPresupuestoProyecto("10000.0");
        proyecto1.setEstadoProyecto(EnumEstadoProyecto.ACEPTADO);
        proyecto1.setCoordinador(coordinador1);  // Asignar coordinador

        Proyecto proyecto2 = new Proyecto();
        proyecto2.setNombreProyecto("Aplicación Móvil");
        proyecto2.setDescripcionProyecto("App para gestión personal");
        proyecto2.setResumenProyecto("Resumen de la app");
        proyecto2.setObjetivoProyecto("Metas específicas");
        proyecto2.setMaximoMesesProyecto("45");
        proyecto2.setPresupuestoProyecto("8000.0");
        proyecto2.setEstadoProyecto(EnumEstadoProyecto.RECIBIDO);
        proyecto2.setCoordinador(coordinador2);  // Asignar coordinador

        proyectoRepositorio.save(proyecto1);
        proyectoRepositorio.save(proyecto2);

        System.out.println("Coordinadores y proyectos precargados en la base de datos H2.");
    }
}


