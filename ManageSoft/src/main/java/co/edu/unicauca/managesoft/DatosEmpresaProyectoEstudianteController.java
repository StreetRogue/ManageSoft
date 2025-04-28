/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package co.edu.unicauca.managesoft;

import co.edu.unicauca.managesoft.access.IEmpresaRepositorio;
import co.edu.unicauca.managesoft.entities.Empresa;
import co.edu.unicauca.managesoft.entities.Proyecto;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author jutak
 */
public class DatosEmpresaProyectoEstudianteController implements Initializable {
    private Empresa empresa;
    private Proyecto proyecto;
    
    public DatosEmpresaProyectoEstudianteController(Empresa empresa, Proyecto proyecto) {
        this.empresa = empresa;
        this.proyecto = proyecto;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Nombre empresa: " + empresa.getNombreEmpresa());
    }
    
}
