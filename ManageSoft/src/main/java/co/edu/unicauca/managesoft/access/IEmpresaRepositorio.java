/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.edu.unicauca.managesoft.access;

import co.edu.unicauca.managesoft.entities.Empresa;
import java.util.List;

/**
 *
 * @author jutak
 */
public interface IEmpresaRepositorio {
    boolean guardar(Empresa nuevaEmpresa);
    Empresa buscarEmpresa(String nombreUsario);
    Empresa buscarEmpresa(String nombreUsario, String contrasenaUsuario);
    List<Empresa> listarEmpresas();
    
    void setRepositorioProyecto(IProyectoRepositorio repositorioProyecto);
}
