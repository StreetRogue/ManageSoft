package co.edu.unicauca.companyServices.services;


import co.edu.unicauca.companyServices.dtos.EmpresaDTO;
import co.edu.unicauca.companyServices.entities.Empresa;
import co.edu.unicauca.companyServices.entities.Proyecto;
import co.edu.unicauca.companyServices.entities.TipoUsuario;
import co.edu.unicauca.companyServices.mappers.ProyectoMapper;
import co.edu.unicauca.companyServices.repositories.EmpresaRepository;
import co.edu.unicauca.companyServices.repositories.ProyectoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpresaService {
    @Autowired
    private final EmpresaRepository empresaRepository;
    @Autowired

    private final ProyectoRepository proyectoRepository;


    private final RabbitMQProducerService rabbitProducer;
    private final ProyectoMapper proyectoMapper;

    public Empresa registrarEmpresa(Empresa empresa) {
        // Verificar si ya existe una empresa con el mismo nit
        if (empresaRepository.existsByNitEmpresa(empresa.getNitEmpresa())) {
            throw new RuntimeException("Ya existe una empresa con el mismo NIT: " + empresa.getNitEmpresa());
        }

        // Verificar si ya existe una empresa con el mismo email
        if (empresaRepository.existsByEmailEmpresa(empresa.getEmailEmpresa())) {
            throw new RuntimeException("Ya existe una empresa con el mismo email: " + empresa.getEmailEmpresa());
        }

        // Verificar si ya existe una empresa con el mismo username
        if (empresaRepository.existsByNombreUsuario(empresa.getNombreUsuario())) {
            throw new RuntimeException("Ya existe una empresa con el mismo nombre de usuario: " + empresa.getNombreUsuario());
        }

        // Si no hay conflictos, guardar la empresa
        return empresaRepository.save(empresa);
    }

    public List<Empresa> listarEmpresas() {
        return empresaRepository.findAll();
    }

    public Empresa obtenerEmpresaPorNit(String nit) {
        return empresaRepository.findById(nit)
                .orElseThrow(() -> new RuntimeException("No se encontró ninguna empresa con el NIT: " + nit));
    }

    @Transactional
    public Proyecto crearProyecto(String nitEmpresa, Proyecto proyecto) {
        Empresa empresa = empresaRepository.findById(nitEmpresa)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        proyecto.setEmpresa(empresa);
        return proyectoRepository.save(proyecto);
    }

    public List<Proyecto> listarProyectosPorEmpresa(String nit) {
        System.out.println("NIT recibido: " + nit);  // Debug

        if (!empresaRepository.existsById(nit)) {
            throw new RuntimeException("Empresa no encontrada");
        }

        Empresa empresa = empresaRepository.findById(nit).get();
        return empresa.getProyectos();  // O como sea que recuperas los proyectos
    }


    public Proyecto obtenerProyecto(Long idProyecto) {
        return proyectoRepository.findById(idProyecto).orElse(null);
    }

    @Transactional
    public Empresa guardarEmpresaDesdeCola(EmpresaDTO empresaDTO) {
        // Convertir DTO a entidad
        Empresa empresa = new Empresa();
        empresa.setNitEmpresa(empresaDTO.getNitEmpresa());
        empresa.setNombreEmpresa(empresaDTO.getNombreEmpresa());
        empresa.setEmailEmpresa(empresaDTO.getEmailEmpresa());
        empresa.setSectorEmpresa(empresaDTO.getSectorEmpresa());
        empresa.setContactoEmpresa(empresaDTO.getTelefonoContactoEmpresa());
        empresa.setNombreContactoEmpresa(empresaDTO.getNombreContactoEmpresa());
        empresa.setApellidoContactoEmpresa(empresaDTO.getApellidoContactoEmpresa());
        empresa.setCargoContactoEmpresa(empresaDTO.getCargoContactoEmpresa());
        empresa.setNombreUsuario(empresaDTO.getNombreUsuario());
        empresa.setContrasenaUsuario(empresaDTO.getContrasenaUsuario());
        empresa.setTipoUsuario(TipoUsuario.EMPRESA);



        // Guardar o actualizar
        return empresaRepository.findById(empresaDTO.getNitEmpresa())
                .map(existing -> {
                    // Actualizar campos existentes
                    existing.setNombreEmpresa(empresa.getNombreEmpresa());
                    existing.setEmailEmpresa(empresa.getEmailEmpresa());
                    // ... otros campos a actualizar
                    return empresaRepository.save(existing);
                })
                .orElseGet(() -> empresaRepository.save(empresa));
    }

    public Empresa buscarPorUsernameYPassword(String username, String password) {
        System.out.println("Buscando empresa con usuario: " + username + " y contraseña: " + password);
        return empresaRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada con las credenciales proporcionadas"));
    }


    public Empresa buscarPorUsername(String username) {
        return empresaRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada con las credenciales proporcionadas"));
    }

    public EmpresaDTO convertirAEmpresaDTO(Empresa empresa) {
        EmpresaDTO dto = new EmpresaDTO();
        dto.setNitEmpresa(empresa.getNitEmpresa());
        dto.setNombreEmpresa(empresa.getNombreEmpresa());
        dto.setEmailEmpresa(empresa.getEmailEmpresa());
        dto.setSectorEmpresa(empresa.getSectorEmpresa());
        dto.setNombreUsuario(empresa.getNombreUsuario());
        dto.setContrasenaUsuario(empresa.getContrasenaUsuario());
        dto.setTipoUsuario(empresa.getTipoUsuario());
        dto.setNombreContactoEmpresa(empresa.getNombreContactoEmpresa());
        dto.setApellidoContactoEmpresa(empresa.getApellidoContactoEmpresa());
        dto.setCargoContactoEmpresa(empresa.getCargoContactoEmpresa());
        dto.setTelefonoContactoEmpresa(empresa.getContactoEmpresa());
        return dto;
    }

}