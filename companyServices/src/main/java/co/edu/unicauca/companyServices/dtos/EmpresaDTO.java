package co.edu.unicauca.companyServices.dtos;


import co.edu.unicauca.companyServices.entities.TipoUsuario;
import lombok.Data;
import java.util.List;

@Data
public class EmpresaDTO {
    private String nombreUsuario;
    private String contrasenaUsuario;
    private TipoUsuario tipoUsuario;
    private String nitEmpresa;
    private String nombreEmpresa;
    private String emailEmpresa;
    private String sectorEmpresa;
    private String telefonoContactoEmpresa;
    private String nombreContactoEmpresa;
    private String apellidoContactoEmpresa;
    private String cargoContactoEmpresa;
}


