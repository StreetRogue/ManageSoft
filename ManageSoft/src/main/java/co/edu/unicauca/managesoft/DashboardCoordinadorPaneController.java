/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package co.edu.unicauca.managesoft;

import co.edu.unicauca.managesoft.access.Repositorio;
import co.edu.unicauca.managesoft.entities.Coordinador;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author juane
 */
public class DashboardCoordinadorPaneController implements Initializable {

    private Repositorio repositorio;
    private Coordinador coordinador;

    public DashboardCoordinadorPaneController(Coordinador coordinador) {
        this.coordinador = coordinador;
    }

    public void setRepositorio(Repositorio repositorio) {
        this.repositorio = repositorio;
    }

    @FXML
    private Text lblCoordinadorName;

    @FXML
    private Text lblCoordinadorApellido;

    @FXML
    private BarChart<String, Number> gphBarChart;

    @FXML
    private Text cantProyectosEvaluados;

    @FXML
    private Text cantEstudiantes;

    @FXML
    private Text tasaProyectosAceptados;

    @FXML
    private Text cantProyectosRechazados;

    @FXML
    private Text cantTiempoAceptacion;

    @FXML
    private Text cantComentarios;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        lblCoordinadorName.setText(coordinador.getNombre());
        lblCoordinadorApellido.setText("Gomez");

        // Estadísticas ficticias
        cantProyectosEvaluados.setText("8");
        
        //Cantidad Estudiantes
        int cantidadEstudiantes = repositorio.getRepositorioEstudiante().cantidadEstudiantes();
        cantEstudiantes.setText(String.valueOf(cantidadEstudiantes));
        
        cantProyectosRechazados.setText("3");
        
        tasaProyectosAceptados.setText("20%");
        
        cantTiempoAceptacion.setText("5");
        
        //Cantidad de Comentarios por Coordinador
        int cantidadComentarios = repositorio.getRepositorioCorreo().cantidadComentarios(coordinador);
        cantComentarios.setText(String.valueOf(cantidadComentarios));

        // Gráfico de barras: estados de proyectos postulados en el período académico actual (2025-1)
        gphBarChart.getData().clear();

        XYChart.Series<String, Number> recibidos = new XYChart.Series<>();
        recibidos.setName("Recibidos");
        recibidos.getData().add(new XYChart.Data<>("2025.1", 10));

        XYChart.Series<String, Number> aceptados = new XYChart.Series<>();
        aceptados.setName("Aceptados");
        aceptados.getData().add(new XYChart.Data<>("2025.1", 12));

        XYChart.Series<String, Number> rechazados = new XYChart.Series<>();
        rechazados.setName("Rechazados");
        rechazados.getData().add(new XYChart.Data<>("2025.1", 30));

        XYChart.Series<String, Number> cerrados = new XYChart.Series<>();
        cerrados.setName("Cerrados");
        cerrados.getData().add(new XYChart.Data<>("2025.1", 20));

        gphBarChart.getData().addAll(recibidos, aceptados, rechazados, cerrados);

    }
}
