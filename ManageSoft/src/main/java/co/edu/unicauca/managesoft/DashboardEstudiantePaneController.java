/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package co.edu.unicauca.managesoft;

import co.edu.unicauca.managesoft.access.Repositorio;
import co.edu.unicauca.managesoft.entities.Estudiante;
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
public class DashboardEstudiantePaneController implements Initializable {

    private Repositorio repositorio;
    private Estudiante estudiante;

    public DashboardEstudiantePaneController(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public void setRepositorio(Repositorio repositorio) {
        this.repositorio = repositorio;
    }

    @FXML
    private Text lblEstudianteName;

    @FXML
    private Text lblEstudianteApellido;

    // Estadísticas
    @FXML
    private Text cantProyectosDisponibles;

    @FXML
    private Text cantProyectosCompletados;

    @FXML
    private Text cantTasaAceptacion;

    @FXML
    private Text cantPostulacionesRechazadas;

    @FXML
    private Text cantPostulaciones;

    // Gráfico de barras
    @FXML
    private BarChart<String, Number> gphBarChart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lblEstudianteName.setText(estudiante.getNombreEstudiante());
        lblEstudianteApellido.setText(estudiante.getApellidoEstudiante());
    }

}
