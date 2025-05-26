/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package co.edu.unicauca.managesoft;

import co.edu.unicauca.managesoft.access.Repositorio;
import co.edu.unicauca.managesoft.entities.Coordinador;
import co.edu.unicauca.managesoft.services.NotificacionServices;
import co.edu.unicauca.managesoft.services.ProyectoServices;
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
    private ProyectoServices proyectoServices;
    private NotificacionServices notificationServices;

    public DashboardCoordinadorPaneController(Coordinador coordinador, Repositorio repositorio) {
        this.coordinador = coordinador;
        this.repositorio = repositorio;
        this.proyectoServices = new ProyectoServices(repositorio.getRepositorioEmpresa().getRepositorioProyecto());
        this.notificationServices = new NotificacionServices(repositorio.getRepositorioCorreo());
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
        lblCoordinadorApellido.setText("");
        String periodoAcademico = "2025-1";

        // Cantidad de Proyectos Evaluados
        int cantidadProyectosEvaluados = proyectoServices.cantProyectosEvaluados();
        cantProyectosEvaluados.setText(String.valueOf(cantidadProyectosEvaluados));
        
        //Cantidad Estudiantes
        int cantidadEstudiantes = repositorio.getRepositorioEstudiante().cantidadEstudiantes();
        cantEstudiantes.setText(String.valueOf(cantidadEstudiantes));
        
        //Cantidad de proyectos rechazados
        int cantidadProyectosRechazados = proyectoServices.cantProyectoporEstado("RECHAZADO", periodoAcademico);
        cantProyectosRechazados.setText(String.valueOf(cantidadProyectosRechazados));
        
        //Tasa Proyectos Aceptados
        int tasaProyectosAceptadosAux = proyectoServices.cantTasaAceptacion();
        tasaProyectosAceptados.setText(String.valueOf(tasaProyectosAceptadosAux)+"%");

        //Cantidad de Tiempo de Aceptacion
        cantTiempoAceptacion.setText("10");//SETEO
        
        //Cantidad de Comentarios por Coordinador
        int cantidadComentarios = notificationServices.cantidadComentarios(coordinador);
        cantComentarios.setText(String.valueOf(cantidadComentarios));

        
        // Gráfico de barras: estados de proyectos postulados en el período académico actual (2025-1)
        int cantidadProyectosRecibidos = proyectoServices.cantProyectoporEstado("RECIBIDO",periodoAcademico);
        int cantidadProyectosAceptados = proyectoServices.cantProyectoporEstado("ACEPTADO",periodoAcademico);
        int cantidadProyectosCerrados = proyectoServices.cantProyectoporEstado("CERRADO",periodoAcademico);
        gphBarChart.getData().clear();

        XYChart.Series<String, Number> recibidos = new XYChart.Series<>();
        recibidos.setName("Recibidos");
        recibidos.getData().add(new XYChart.Data<>(periodoAcademico, cantidadProyectosRecibidos));

        XYChart.Series<String, Number> aceptados = new XYChart.Series<>();
        aceptados.setName("Aceptados");
        aceptados.getData().add(new XYChart.Data<>(periodoAcademico, cantidadProyectosAceptados));

        XYChart.Series<String, Number> rechazados = new XYChart.Series<>();
        rechazados.setName("Rechazados");
        rechazados.getData().add(new XYChart.Data<>(periodoAcademico, cantidadProyectosRechazados));

        XYChart.Series<String, Number> cerrados = new XYChart.Series<>();
        cerrados.setName("Cerrados");
        cerrados.getData().add(new XYChart.Data<>(periodoAcademico, cantidadProyectosCerrados));

        gphBarChart.getData().addAll(recibidos, aceptados, rechazados, cerrados);

    }
}
