/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package co.edu.unicauca.managesoft;

import co.edu.unicauca.managesoft.access.Repositorio;
import co.edu.unicauca.managesoft.entities.Coordinador;
import co.edu.unicauca.managesoft.entities.IEstadoProyecto;
import co.edu.unicauca.managesoft.services.NotificacionServices;
import co.edu.unicauca.managesoft.services.ProyectoServices;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
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

    @FXML
    private ComboBox<String> cboPeriodoAcademico;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configurar ComboBox con períodos académicos
        cboPeriodoAcademico.getItems().addAll("2025-1", "2025-2");
        cboPeriodoAcademico.setValue("2025-1"); // Establecer valor por defecto

        // Mostrar información del coordinador
        lblCoordinadorName.setText(coordinador.getNombre());
        lblCoordinadorApellido.setText(" "); // Asumiendo que existe este campo

        // Cargar datos iniciales
        cargarDatosDashboard(cboPeriodoAcademico.getValue());

        // Configurar listener para cambios en el ComboBox
        cboPeriodoAcademico.setOnAction(event -> {
            cargarDatosDashboard(cboPeriodoAcademico.getValue());
        });
    }

    private void cargarDatosDashboard(String periodoAcademico) {
        // Cantidad de Proyectos Evaluados (total, no depende del período)
        int cantidadProyectosEvaluados = proyectoServices.cantProyectosEvaluados();
        cantProyectosEvaluados.setText(String.valueOf(cantidadProyectosEvaluados));

        // Cantidad Estudiantes (total, no depende del período)
        int cantidadEstudiantes = repositorio.getRepositorioEstudiante().cantidadEstudiantes();
        cantEstudiantes.setText(String.valueOf(cantidadEstudiantes));

        // Proyectos rechazados (depende del período)
        int cantidadProyectosRechazados = proyectoServices.cantProyectoporEstado("RECHAZADO", periodoAcademico);
        cantProyectosRechazados.setText(String.valueOf(cantidadProyectosRechazados));

        // Tasa Proyectos Aceptados (total o por período, según tu lógica)
        int tasaProyectosAceptadosAux = proyectoServices.cantTasaAceptacion();
        tasaProyectosAceptados.setText(tasaProyectosAceptadosAux + "%");

        // Tiempo promedio de aceptación (ajusta según tu lógica real)
        cantTiempoAceptacion.setText(String.valueOf(proyectoServices.avgTiempoAceptacion()));

        // Cantidad de Comentarios por Coordinador
        int cantidadComentarios = notificationServices.cantidadComentarios(coordinador);
        cantComentarios.setText(String.valueOf(cantidadComentarios));

        // Actualizar gráfico
        actualizarGraficoEstadosProyectos(periodoAcademico);
    }

    private void actualizarGraficoEstadosProyectos(String periodoAcademico) {
        // Obtener cantidades por estado
        int cantidadProyectosRecibidos = proyectoServices.cantProyectoporEstado("RECIBIDO", periodoAcademico);
        int cantidadProyectosAceptados = proyectoServices.cantProyectoporEstado("ACEPTADO", periodoAcademico);
        int cantidadProyectosRechazados = proyectoServices.cantProyectoporEstado("RECHAZADO", periodoAcademico);
        int cantidadProyectosCerrados = proyectoServices.cantProyectoporEstado("CERRADO", periodoAcademico);

        // Limpiar gráfico existente
        gphBarChart.getData().clear();

        // Crear series para cada estado
        XYChart.Series<String, Number> seriesRecibidos = crearSerie("Recibidos", periodoAcademico, cantidadProyectosRecibidos);
        XYChart.Series<String, Number> seriesAceptados = crearSerie("Aceptados", periodoAcademico, cantidadProyectosAceptados);
        XYChart.Series<String, Number> seriesRechazados = crearSerie("Rechazados", periodoAcademico, cantidadProyectosRechazados);
        XYChart.Series<String, Number> seriesCerrados = crearSerie("Cerrados", periodoAcademico, cantidadProyectosCerrados);

        // Agregar series al gráfico
        gphBarChart.getData().addAll(seriesRecibidos, seriesAceptados, seriesRechazados, seriesCerrados);

        // Opcional: Personalizar aspecto del gráfico
        gphBarChart.setLegendVisible(true);
        gphBarChart.setAnimated(false);
    }

    private XYChart.Series<String, Number> crearSerie(String nombre, String periodo, int valor) {
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName(nombre);
        serie.getData().add(new XYChart.Data<>(periodo, valor));
        return serie;
    }

    /*public void initialize(URL location, ResourceBundle resources) {
        
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

    }*/
}
