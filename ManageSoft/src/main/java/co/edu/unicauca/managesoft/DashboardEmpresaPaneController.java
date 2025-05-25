/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package co.edu.unicauca.managesoft;

import co.edu.unicauca.managesoft.access.Repositorio;
import co.edu.unicauca.managesoft.entities.Empresa;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author juane
 */
public class DashboardEmpresaPaneController implements Initializable {

    private Repositorio repositorio;
    private Empresa empresa;

    public DashboardEmpresaPaneController(Empresa empresa) {
        this.empresa = empresa;
    }

    public void setRepositorio(Repositorio repositorio) {
        this.repositorio = repositorio;
    }

    @FXML
    private Text lblCompanyName;

    @FXML
    private Text lblRepreName;

    @FXML
    private Text lblLastNameRepre;

    @FXML
    private Text cantProyectosPostulados;

    @FXML
    private Text cantEstudiantesPostulados;

    @FXML
    private Text cantTasaAceptacion;

    @FXML
    private Text cantProyectosRechazados;

    @FXML
    private Text cantTiempoAceptacion;

    @FXML
    private BarChart<String, Number> gphBarChart;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lblCompanyName.setText(empresa.getNombreEmpresa());
        lblRepreName.setText(empresa.getNombreContactoEmpresa());
        lblLastNameRepre.setText(empresa.getApellidoContactoEmpresa());
    }

}
