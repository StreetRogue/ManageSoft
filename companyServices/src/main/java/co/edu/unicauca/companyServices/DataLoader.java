package co.edu.unicauca.companyServices;


import co.edu.unicauca.companyServices.entities.Empresa;
import co.edu.unicauca.companyServices.entities.Proyecto;
import co.edu.unicauca.companyServices.entities.EstadoProyecto;
import co.edu.unicauca.companyServices.repositories.EmpresaRepository;
import co.edu.unicauca.companyServices.repositories.ProyectoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

@Component
public class DataLoader {

    private final EmpresaRepository empresaRepository;
    private final ProyectoRepository proyectoRepository;

    public DataLoader(EmpresaRepository empresaRepository, ProyectoRepository proyectoRepository) {
        this.empresaRepository = empresaRepository;
        this.proyectoRepository = proyectoRepository;
    }

    @PostConstruct
    public void loadInitialData() {
        if (empresaRepository.count() == 0) {
            // Crear empresas usando constructores y setters
            Empresa empresa1 = new Empresa();
            empresa1.setNitEmpresa("123456789-1");
            empresa1.setNombreEmpresa("Tech Solutions SAS");
            empresa1.setEmailEmpresa("contacto@techsolutions.com");
            empresa1.setSectorEmpresa("Tecnología");
            empresa1.setContactoEmpresa("3124567890");
            empresa1.setNombreContactoEmpresa("Ana");
            empresa1.setApellidoContactoEmpresa("Gómez");
            empresa1.setCargoContactoEmpresa("Gerente de Proyectos");
            empresa1.setNombreUsuario("techsolutions");
            empresa1.setContrasenaUsuario("password123");

            Empresa empresa2 = new Empresa();
            empresa2.setNitEmpresa("987654321-2");
            empresa2.setNombreEmpresa("Innovatech Ltda");
            empresa2.setEmailEmpresa("info@innovatech.com");
            empresa2.setSectorEmpresa("Desarrollo Software");
            empresa2.setContactoEmpresa("3201234567");
            empresa2.setNombreContactoEmpresa("Carlos");
            empresa2.setApellidoContactoEmpresa("Martínez");
            empresa2.setCargoContactoEmpresa("Director de Innovación");
            empresa2.setNombreUsuario("innovatech");
            empresa2.setContrasenaUsuario("password456");

            // Guardar empresas
            empresaRepository.saveAll(Arrays.asList(empresa1, empresa2));

            // Crear proyectos usando constructores
            Proyecto proyecto1 = new Proyecto();
            proyecto1.setNombreProyecto("Sistema de Gestión Académica");
            proyecto1.setResumenProyecto("Plataforma para gestión de procesos académicos");
            proyecto1.setObjetivoProyecto("Automatizar procesos de registro y seguimiento");
            proyecto1.setDescripcionProyecto("El sistema debe permitir matrículas, registro de notas, generación de reportes");
            proyecto1.setMaximoMesesProyecto("6");
            proyecto1.setPresupuestoProyecto("15000.0");
            proyecto1.setFechaPublicacionProyecto(LocalDate.now());
            proyecto1.setEstadoProyecto(EstadoProyecto.RECIBIDO);
            proyecto1.setEmpresa(empresa1);

            Proyecto proyecto2 = new Proyecto();
            proyecto2.setNombreProyecto("App Móvil para Eventos");
            proyecto2.setResumenProyecto("Aplicación móvil para gestión de eventos universitarios");
            proyecto2.setObjetivoProyecto("Facilitar la organización y participación en eventos");
            proyecto2.setDescripcionProyecto("La app debe incluir calendario, registro, notificaciones y encuestas");
            proyecto2.setMaximoMesesProyecto("4");
            proyecto2.setPresupuestoProyecto("8000.0");
            proyecto2.setFechaPublicacionProyecto(LocalDate.now());
            proyecto2.setEstadoProyecto(EstadoProyecto.RECIBIDO);
            proyecto2.setEmpresa(empresa1);

            Proyecto proyecto3 = new Proyecto();
            proyecto3.setNombreProyecto("Plataforma E-learning");
            proyecto3.setResumenProyecto("Sistema de educación virtual");
            proyecto3.setObjetivoProyecto("Implementar solución de aprendizaje en línea");
            proyecto3.setDescripcionProyecto("Debe incluir gestión de cursos, estudiantes, evaluaciones y certificados");
            proyecto3.setMaximoMesesProyecto("8");
            proyecto3.setPresupuestoProyecto("25000.0");
            proyecto3.setFechaPublicacionProyecto(LocalDate.now());
            proyecto3.setEstadoProyecto(EstadoProyecto.RECIBIDO);
            proyecto3.setEmpresa(empresa2);

            // Guardar proyectos
            proyectoRepository.saveAll(Arrays.asList(proyecto1, proyecto2, proyecto3));
        }
    }
}