/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.services;

import co.edu.unicauca.managesoft.access.IEmpresaRepositorio;
import co.edu.unicauca.managesoft.entities.Empresa;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jutak
 */
public class EmpresaServices {
    private IEmpresaRepositorio repositorio;

    public EmpresaServices(IEmpresaRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public List<Empresa> getEmpresas() {
        List<Empresa> empresas = new ArrayList<>();
        empresas = repositorio.listarEmpresas();
        return empresas;
    }

    public boolean guardarEmpresa(Empresa nuevaEmpresa) {
        /* CompanyValidation newValidation = new CompanyValidation(nuevaEmpresa);
        newValidation.isValid(); */
        return repositorio.guardar(nuevaEmpresa);
    }
}
