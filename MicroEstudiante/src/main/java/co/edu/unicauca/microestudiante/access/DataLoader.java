package co.edu.unicauca.microestudiante.access;

import co.edu.unicauca.microestudiante.entities.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    IProyectoRepositorio projectRepositorrio;
    @Autowired
    IEstudianteRepositorio estudianteRepositorio;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        projectRepositorrio.deleteAll();

        Estudiante student1 = new Estudiante( 1l, "Juan", "Perez", "juan@example.com");
        Estudiante student2 = new Estudiante(2l,"Maria", "Lopez", "maria.lopez@example.com");
        Estudiante student3 = new Estudiante(3l, "Carlos", "Gonzalez", "carlos.gonzalez@example.com");
        estudianteRepositorio.save(student1);
        estudianteRepositorio.save(student2);
        estudianteRepositorio.save(student3);

        // Proyecto 1 con estudiante postulado
        Proyecto proyecto1 = new Proyecto();
        proyecto1.setNombreProyecto("Sistema de Gestión");
        proyecto1.setDescripcionProyecto("Sistema para gestionar recursos");
        proyecto1.setResumenProyecto("Resumen del sistema");
        proyecto1.setObjetivoProyecto("Objetivos claros");
        proyecto1.setMaximoMesesProyecto("30");
        proyecto1.setPresupuestoProyecto("10000.0");
        proyecto1.setEstadoProyecto(enumEstadoProyecto.ACEPTADO);
        proyecto1.getEstudiantesPostulados().add(student1);
        projectRepositorrio.save(proyecto1);

        // Proyecto 2 con estudiante aprobado
        Proyecto proyecto2 = new Proyecto();
        proyecto2.setNombreProyecto("Aplicación Móvil");
        proyecto2.setDescripcionProyecto("App para gestión personal");
        proyecto2.setResumenProyecto("Resumen de la app");
        proyecto2.setObjetivoProyecto("Metas específicas");
        proyecto2.setMaximoMesesProyecto("45");
        proyecto2.setPresupuestoProyecto("8000.0");
        proyecto2.setEstadoProyecto(enumEstadoProyecto.ACEPTADO);

        // Asociar estudiante2 como aprobado
        proyecto2.getEstudiantesAceptados().add(student2);
        projectRepositorrio.save(proyecto2);

        student2.postularse(proyecto2);
    }
}
