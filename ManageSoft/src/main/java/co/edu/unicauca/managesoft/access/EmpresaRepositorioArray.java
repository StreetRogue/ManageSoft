/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.Empresa;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jutak
 */
public class EmpresaRepositorioArray implements IEmpresaRepositorio {
    public static List<Empresa> empresasArray;

    public EmpresaRepositorioArray() {
        empresasArray = new ArrayList<>();
        empresasArray.add(new Empresa("123", "TechCorp", "contacto@techcorp.com", "Tecnología", "1234567890", "Juan", "Pérez", "Gerente", "techcorp", "password123"));
        empresasArray.add(new Empresa("987", "EcoSolutions", "info@ecosolutions.com", "Medio Ambiente", "3216549870", "María", "Gómez", "Directora", "ecosolutions", "securepass"));
        empresasArray.add(new Empresa("445", "BuildIt", "contacto@buildit.com", "Construcción", "5556667777", "Carlos", "Lopez", "Supervisor", "buildit", "buildit123"));
        empresasArray.add(new Empresa("889", "HealthCare Inc.", "support@healthcare.com", "Salud", "4448889999", "Ana", "Martínez", "Médica Jefe", "healthcare", "health2023"));
        empresasArray.add(new Empresa("665", "AgroFuture", "info@agrofuture.com", "Agricultura", "1112223333", "Luis", "Rodríguez", "Agrónomo", "agrofuture", "future123"));
        empresasArray.add(new Empresa("321", "empresa Versaculo", "empresa1@gmail.com", "Educacion", "3164171685", "Juan Diego", "Perez Martinez", "Jefe", "empresa1", "pass789"));
    }

    @Override
    public boolean guardar(Empresa nuevaEmpresa) {
        if (!existeNit(nuevaEmpresa.getNitEmpresa())){
            empresasArray.add(nuevaEmpresa);
            return true;
        }
        return false;
    }
    
    @Override
    public Empresa buscarEmpresa(String nombreUsuario) {
        for (Empresa empresa : empresasArray) {
            if (empresa.getNombreUsuario().equals(nombreUsuario)) {
                return empresa;
            }
        }
        
        return null;
    }
    
    @Override
    public Empresa buscarEmpresa(String nombreUsuario, String contrasenaUsuario) {
        for (Empresa empresa : empresasArray) {
            if (empresa.getNombreUsuario().equals(nombreUsuario) && empresa.getContrasenaUsuario().equals(contrasenaUsuario)) {
                return empresa;
            }
        }
        
        return null;
    }

    @Override
    public List<Empresa> listarEmpresas() {
        return empresasArray;
    }
    
    private boolean existeNit(String nit){
        for (Empresa empresa: empresasArray){
            if (empresa.getNitEmpresa().equals(nit)){
                return true;
            }
        }
        return false;
    }
    
}
